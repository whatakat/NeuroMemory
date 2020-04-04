package com.bankmtk.neuromemory.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.StickerResult
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import java.lang.Exception

private const val STICKERS_COLLECTION = "stickers"

class FireStoreProvider : RemoteDataProvider {
    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val db = FirebaseFirestore.getInstance()
    private val stickersReference = db.collection(STICKERS_COLLECTION)

    override fun saveSticker(sticker: Sticker): LiveData<StickerResult> =
        MutableLiveData<StickerResult>().apply {
            stickersReference.document(sticker.id)
                .set(sticker).addOnSuccessListener {
                    Log.d(TAG, "Sticker $sticker is saved")
                    value = StickerResult.Success(sticker)
                }.addOnFailureListener {
                    Log.d(TAG, "Error saving sticker $sticker, message: ${it.message}")
                    value = StickerResult.Error(it)
                }
        }

    override fun getStickerById(id: String): LiveData<StickerResult> =
        MutableLiveData<StickerResult>().apply {
            stickersReference.document(id).get()
                .addOnSuccessListener {
                    value =
                        StickerResult.Success(it.toObject(Sticker::class.java))
                }.addOnFailureListener {
                    value = StickerResult.Error(it)
                }
        }

    override fun subscribeToAllStickers(): LiveData<StickerResult> =
        MutableLiveData<StickerResult>().apply {
            stickersReference.addSnapshotListener { snapshot, e ->
                value = e?.let { StickerResult.Error(it) }
                    ?: snapshot?.let {
                        val stickers = it.documents.map {
                            it.toObject(Sticker::class.java)
                        }
                        StickerResult.Success(stickers)
                    }
            }
        }
}