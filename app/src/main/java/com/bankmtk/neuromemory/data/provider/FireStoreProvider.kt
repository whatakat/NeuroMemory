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

    override fun saveSticker(sticker: Sticker): LiveData<StickerResult> {
        val result = MutableLiveData<StickerResult>()
        stickersReference.document(sticker.id)
            .set(sticker).addOnSuccessListener {
                Log.d(TAG, "Sticker $sticker is saved")
                result.value = StickerResult.Success(sticker)
            }.addOnFailureListener{
                OnFailureListener { p0 ->
                    Log.d(TAG, "Error saving sticker $sticker, message: ${p0.message}")
                    result.value = StickerResult.Error(p0)
                }
            }
        return result
    }

    override fun getStickerById(id: String): LiveData<StickerResult> {
        val result = MutableLiveData<StickerResult>()
        stickersReference.document(id).get()
            .addOnSuccessListener { snapshot ->
                result.value =
                    StickerResult.Success(snapshot.toObject(Sticker::class.java))
            }.addOnFailureListener{result.value = StickerResult.Error(it)}
        return result
    }

    override fun subscribeToAllStickers(): LiveData<StickerResult> {
        val result = MutableLiveData<StickerResult>()

        stickersReference.addSnapshotListener { snapshot, e ->
            if (e != null){
                result.value = StickerResult.Error(e)
            }else if (snapshot != null){
                val stickers = mutableListOf<Sticker>()

                for (doc: QueryDocumentSnapshot in snapshot){
                    stickers.add(doc.toObject(Sticker::class.java))
                }
                result.value = StickerResult.Success(stickers)
            }
        }
        return result
    }
}