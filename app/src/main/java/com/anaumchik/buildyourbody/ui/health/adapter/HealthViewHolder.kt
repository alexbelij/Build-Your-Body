package com.anaumchik.buildyourbody.ui.health.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anaumchik.buildyourbody.R
import com.anaumchik.buildyourbody.data.entity.Health
import com.anaumchik.buildyourbody.data.entity.UpdateHealth
import com.anaumchik.buildyourbody.data.entity.createUpdateHealth
import com.anaumchik.buildyourbody.data.utils.SessionManager
import com.anaumchik.buildyourbody.data.utils.background
import com.anaumchik.buildyourbody.data.utils.loadDrawable
import com.anaumchik.buildyourbody.data.utils.toast
import kotlinx.android.synthetic.main.item_health.view.imgIcon
import kotlinx.android.synthetic.main.item_health.view.tvAdjustHealth
import kotlinx.android.synthetic.main.item_health.view.tvCostMoney
import kotlinx.android.synthetic.main.item_health.view.tvDescription
import kotlinx.android.synthetic.main.item_health.view.tvMinLvl
import kotlinx.android.synthetic.main.item_health.view.tvTitle
import org.koin.core.KoinComponent
import org.koin.core.inject

class HealthViewHolder(private val view: View) : RecyclerView.ViewHolder(view), KoinComponent {
    private val playerSession = inject<SessionManager>().value

    private lateinit var listener: HealthAdapterListener
    private lateinit var health: Health
    private var imgIcon: ImageView = view.imgIcon
    private var tvTitle: TextView = view.tvTitle
    private var tvDescription: TextView = view.tvDescription
    private var tvAdjustHealth: TextView = view.tvAdjustHealth
    private var tvCostMoney: TextView = view.tvCostMoney
    private var tvMinLvl: TextView = view.tvMinLvl

    fun initViewHolder(
        health: Health,
        listener: HealthAdapterListener
    ) {
        this.health = health
        this.listener = listener
        tvTitle.text = health.title
        tvDescription.text = health.description
        tvAdjustHealth.text = "+${health.adjustHealth}hp"
        tvCostMoney.text = "-${health.costMoney}$"
        imgIcon.loadDrawable(R.drawable.ic_bodybuilder)

        when {
            isNotEnoughLvl() -> errorNotEnoughLvlAction()
            isNotEnoughMoney() -> errorNotEnoughMoneyAction()
            isNotEnoughTime() -> errorNotEnoughTimeAction()
            else -> performSuccessHealthAction()
        }
    }

    private fun isNotEnoughMoney(): Boolean = playerSession.player.money < health.costMoney

    private fun isNotEnoughLvl(): Boolean = playerSession.player.lvl < health.minLvl

    private fun isNotEnoughTime(): Boolean = playerSession.player.time == 0

    private fun errorNotEnoughLvlAction() {
        view.setOnClickListener { }
        view.background(R.color.gray_light)
        tvMinLvl.text = "${health.minLvl} lvl"
        tvMinLvl.visibility = View.VISIBLE
        view.setOnClickListener {} // clear click listener
    }

    private fun errorNotEnoughMoneyAction() =
        view.setOnClickListener { view.toast(R.string.health_action_not_enough_money) }

    private fun errorNotEnoughTimeAction() =
        view.setOnClickListener { view.toast(R.string.health_action_not_enough_time) }

    private fun performSuccessHealthAction() =
        view.setOnClickListener { listener.onClick(health.createUpdateHealth()) }
}

interface HealthAdapterListener {
    fun onClick(update: UpdateHealth)
}
