package com.barryzhang.gankkotlin.ui.main

import com.barryzhang.gankkotlin.api.SimpleSubscriber
import com.barryzhang.gankkotlin.data.MainRepository
import com.barryzhang.gankkotlin.entities.BeautyData
import com.barryzhang.gankkotlin.entities.DailyGankEntity
import com.barryzhang.gankkotlin.entities.DayContent
import com.barryzhang.gankkotlin.entities.GankDate
import com.barryzhang.gankkotlin.ext.concat
import com.barryzhang.gankkotlin.ext.registerEventBus
import com.barryzhang.gankkotlin.ext.unregisterEventBus
import org.greenrobot.eventbus.Subscribe
import rx.Subscriber
import java.util.*

/**
 * barryhappy2010@gmail.com
 * https://github.com/barryhappy
 * http://www.barryzhang.com/
 * Created by Barry on 16/7/15 12:17.
 */

class MainPresenter : MainContract.Presenter {
    val m: MainRepository
    val v: MainContract.View

    constructor(repository: MainRepository,
                view: MainContract.View) {
        m = repository
        v = view
        v.setPresenter(this)
    }

    override fun start() {
        registerEventBus()
    }

    @Subscribe
    fun onNewDateSelectEvent(date : GankDate){
        getRemoteData(date)
        getTitle(date)
    }

    override fun getRemoteData(day: GankDate) {

        val subscriber = object : SimpleSubscriber<DailyGankEntity>() {
            override fun onNext(t: DailyGankEntity) {
                val list = ArrayList<Any>()
                t.category?.forEach {
                    var topIndex = 0
                    when (it) {
                        "福利" -> {
                            list.add(topIndex++, it)
                            list.addAll(topIndex, t.results?.get(it)?.
                                    map { it -> BeautyData.create("FFF", it) }!!)
                        }
                        else -> {
                            list.add(it)
                            list.addAll(t.results?.get(it)!!)
                        }
                    }
                }

                v.showList(list)
            }

        }
        subscriberList.add(subscriber)
        m.getDailyGankEntity(day, subscriber)
    }

    override fun getTitle(day: GankDate) {
        v.setTitle("首页 ${day.toMD()}" )

//        val subscriber : Subscriber<DayContent> = object : SimpleSubscriber<DayContent> (){
//            override fun onNext(t: DayContent) {
//                v.setTitle(t.results?.first()?.title)
//            }
//        }
//        m.getDayContent(day,subscriber)
    }

    override fun release() {
        super.release()
        unregisterEventBus()
    }


}