package com.wakuei.githubuserlistpresent.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wakuei.githubuserlistpresent.databinding.ItemUserLeftBinding
import com.wakuei.githubuserlistpresent.databinding.ItemUserRightBinding
import com.wakuei.githubuserlistpresent.model.UserModel
import com.wakuei.githubuserlistpresent.network.Config

class UserAdapter(private val mContext: Context) :
    ListAdapter<UserModel, RecyclerView.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Config.ITEM_LEFT ->
                LeftViewHolder(ItemUserLeftBinding.inflate(LayoutInflater.from(mContext)), mContext)
            else ->
                RightViewHolder(
                    ItemUserRightBinding.inflate(LayoutInflater.from(mContext)),
                    mContext
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LeftViewHolder ->
                holder.bind(getItem(position))
            else ->
                (holder as RightViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 1) Config.ITEM_RIGHT else Config.ITEM_LEFT
    }

    override fun submitList(list: List<UserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    class LeftViewHolder(
        itemView: ItemUserLeftBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemView.root) {
        private val mBinding = itemView
        fun bind(data: UserModel) {
            mBinding.txtId.text = data.id.toString()
            mBinding.txtName.text = data.login
            mBinding.txtUrl.text = data.html_url
            Glide.with(context)
                .load(data.avatar_url)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(30)))
                .into(mBinding.imgPhoto)
        }
    }

    class RightViewHolder(
        itemView: ItemUserRightBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemView.root) {
        private val mBinding = itemView
        fun bind(data: UserModel) {
            mBinding.txtId.text = data.id.toString()
            mBinding.txtName.text = data.login
            mBinding.txtUrl.text = data.html_url
            Glide.with(context)
                .load(data.avatar_url)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(30)))
                .into(mBinding.imgPhoto)
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }
}