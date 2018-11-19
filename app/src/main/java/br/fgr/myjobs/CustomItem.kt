package br.fgr.myjobs

import com.google.firebase.Timestamp

data class CustomItem(val ts:Timestamp?,
                      val title: String?,
                      val description: String?,
                      val result: String?,
                      val status: Long?)