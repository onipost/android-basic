package com.example.com.presentation.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.com.BR

open class BaseAdapter : RecyclerView.Adapter<ViewHolder>() {

    /**
     * [HashMap] possible cells. key - view model, [CellInfo] - value
     */
    val cellMap = hashMapOf<Class<out Any>, CellInfo>()

    /**
     * List of data
     */
    var items = arrayListOf<Any>()
        set(value) {
            val diffCallback = DiffCallBack(
                items,
                value,
                checkAreItemsTheSame = { oldItem: Any, newItem: Any ->
                    getCellInfo(oldItem).checkAreItemsTheSame.invoke(oldItem, newItem)
                },
                checkAreContentsTheSame = { oldItem: Any, newItem: Any ->
                    getCellInfo(oldItem).checkAreContentsTheSame.invoke(oldItem, newItem)
                }
            )
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            diffResult.dispatchUpdatesTo(
                object : ListUpdateCallback {
                    override fun onInserted(position: Int, count: Int) {
                        notifyItemRangeInserted(position, count)
                    }

                    override fun onRemoved(position: Int, count: Int) {
                        notifyItemRangeRemoved(position, count)
                    }

                    override fun onMoved(fromPosition: Int, toPosition: Int) {
                        notifyItemMoved(fromPosition, toPosition)
                    }

                    override fun onChanged(position: Int, count: Int, payload: Any?) {
                        notifyItemRangeChanged(position, count, payload)
                    }
                }
            )
        }

    inline fun <reified T : Any> registerCell(
        @LayoutRes layoutId: Int,
        bindingId: Int = BR.viewModel,
        noinline areItemsTheSame: (T, T) -> Boolean = { a: T, b: T -> a == b },
        noinline areContentsTheSame: (T, T) -> Boolean = { a: T, b: T -> a.hashCode() == b.hashCode() }
    ) {
        val cellInfo = CellInfo(
            layoutId,
            bindingId,
            areItemsTheSame as (Any, Any) -> Boolean,
            areContentsTheSame as (Any, Any) -> Boolean
        )
        cellMap[T::class.java] = cellInfo
    }

    protected fun getViewModel(position: Int) = items[position]

    private fun getCellInfo(viewModel: Any): CellInfo {
        cellMap.entries.find { it.key == viewModel.javaClass }?.apply { return value }

        cellMap.entries.find { it.key.isInstance(viewModel) }?.apply {
            cellMap[viewModel.javaClass] = value
            return value
        }

        throw Exception("Cell not found.")
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = getCellInfo(getViewModel(position)).layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ViewHolder(view.rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellInfo = getCellInfo(getViewModel(position))
        if (cellInfo.bindingId != 0) {
            holder.binding.setVariable(cellInfo.bindingId, getViewModel(position))
            holder.binding.executePendingBindings()
        }
    }

    inner class DiffCallBack(
        private val oldList: List<Any>,
        private val newList: List<Any>,
        private val checkAreItemsTheSame: (Any, Any) -> Boolean,
        private val checkAreContentsTheSame: (Any, Any) -> Boolean
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
            val old = oldList[oldPos]
            val new = newList[newPos]
            if (old::class != new::class) {
                return false
            }
            return checkAreItemsTheSame(old, new)
        }

        override fun areContentsTheSame(oldPos: Int, newPos: Int) =
            checkAreContentsTheSame(oldList[oldPos], newList[newPos])
    }
}

/**
 * Bindable holder.
 * @param view bindable view
 */
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ViewDataBinding = DataBindingUtil.bind(view)!!
}

/**
 * Info of possible cell
 * @param layoutId id of layout
 * @param bindingId id of binding variable, like BR.viewModel
 */
data class CellInfo(
    val layoutId: Int,
    val bindingId: Int,
    val checkAreItemsTheSame: (Any, Any) -> Boolean,
    val checkAreContentsTheSame: (Any, Any) -> Boolean
)
