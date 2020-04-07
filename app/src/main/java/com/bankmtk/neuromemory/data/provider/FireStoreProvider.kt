package com.bankmtk.neuromemory.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bankmtk.neuromemory.data.errors.NoAuthException
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.StickerResult
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.lang.Exception

private const val STICKERS_COLLECTION = "stickers"
private const val USERS_COLLECTION = "users"

class FireStoreProvider : RemoteDataProvider {
    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val db = FirebaseFirestore.getInstance()
    private val stickersReference = db.collection(STICKERS_COLLECTION)

    private val currentUser get() = FirebaseAuth.getInstance().currentUser

    private fun getUserStickersCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(STICKERS_COLLECTION)
    } ?: throw NoAuthException()

    override fun saveSticker(sticker: Sticker): LiveData<StickerResult> =
        MutableLiveData<StickerResult>().apply {
            try {
                getUserStickersCollection().document(sticker.id)
                    .set(sticker).addOnSuccessListener {
                        Log.d(TAG,"Sticker $sticker is saved")
                        value = StickerResult.Success(sticker)
                    }.addOnFailureListener {
                        Log.d(TAG, "Error saving sticker $sticker, message: ${it.message}")
                        throw it
                    }
            } catch (e: Throwable){
                value = StickerResult.Error(e)
            }
        }

    override fun getStickerById(id: String): LiveData<StickerResult> =
        MutableLiveData<StickerResult>().apply {
            try {
                getUserStickersCollection().document(id).get()
                    .addOnSuccessListener {
                        value =
                            StickerResult.Success(it.toObject(Sticker::class.java))
                    }.addOnFailureListener {
                        throw it
                    }
            }catch (e: Throwable){
                value = StickerResult.Error(e)
            }
        }

    override fun subscribeToAllStickers(): LiveData<StickerResult> =
        MutableLiveData<StickerResult>().apply {
            try {
                getUserStickersCollection().addSnapshotListener{snapshot, e->
                    value = e?.let { throw it }
                        ?: snapshot?.let {
                            val stickers = it.documents.map {
                                it.toObject(Sticker::class.java)}
                            StickerResult.Success(stickers)
                        }
                }
            }catch (e: Throwable){
                value = StickerResult.Error(e)
            }
        }
}