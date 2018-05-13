package com.example.bernardoaltamirano.quiniela.main.quiniela.detail

import com.example.bernardoaltamirano.quiniela.di.ScreenScope
import com.example.bernardoaltamirano.quiniela.model.Quiniela
import com.example.bernardoaltamirano.quiniela.model.User
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import timber.log.Timber
import kotlin.reflect.jvm.internal.impl.javax.inject.Inject

@ScreenScope
class QuinielaDetailsViewModel @Inject constructor() {

    private val detailStateRelay: BehaviorRelay<QuinielaDetailState> = BehaviorRelay.create()
    private val memberStateRelay: BehaviorRelay<MemberState> = BehaviorRelay.create()

    init {
        detailStateRelay.accept(QuinielaDetailState(true))
        memberStateRelay.accept(MemberState(true))
    }

    fun details(): Observable<QuinielaDetailState> {
        return detailStateRelay
    }

    fun members(): Observable<MemberState> {
        return memberStateRelay
    }

    fun processQuiniela(): Consumer<Quiniela> {
        return Consumer {
            detailStateRelay.accept(QuinielaDetailState(false, it.id, it.price, it.name))
        }
    }

    fun processMembers(): Consumer<List<User>> {
        return Consumer {
            memberStateRelay.accept(MemberState(false, it))
        }
    }

    fun detailsError(): Consumer<Throwable> {
        return Consumer {
            Timber.e(it, "Error loading quiniela details")
            detailStateRelay.accept(QuinielaDetailState(false, error = it.message))
        }
    }

    fun membersError(): Consumer<Throwable> {
        return Consumer {
            Timber.e(it, "Error loading members")
            memberStateRelay.accept(MemberState(false, error = it.message))
        }
    }
}