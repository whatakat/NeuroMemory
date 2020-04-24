package com.bankmtk.neuromemory.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bankmtk.neuromemory.data.errors.NoAuthException
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val STICKERS_COLLECTION = "stickers"
private const val USERS_COLLECTION = "users"

class FireStoreProvider(private val firebaseAuth: FirebaseAuth, private val db:FirebaseFirestore) : RemoteDataProvider {
    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

   // private val db = FirebaseFirestore.getInstance()
    private val stickersReference = db.collection(STICKERS_COLLECTION)

    private val currentUser get() = FirebaseAuth.getInstance().currentUser

    private fun getUserStickersCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(STICKERS_COLLECTION)
    } ?: throw NoAuthException()

    override fun saveSticker(sticker: Sticker): LiveData<Result> =
        MutableLiveData<Result>().apply {
            try {
                getUserStickersCollection().document(sticker.id)
                    .set(sticker).addOnSuccessListener {
                        Log.d(TAG,"Sticker $sticker is saved")
                        value = Result.Success(sticker)
                    }.addOnFailureListener {
                        Log.d(TAG, "Error saving sticker $sticker, message: ${it.message}")
                        throw it
                    }
            } catch (e: Throwable){
                value = Result.Error(e)
            }
        }

    override suspend fun getStickerById(id: String): Sticker =
        suspendCoroutine { continuation ->
            try {
                getUserStickersCollection().document(id).get()
                    .addOnSuccessListener {
                        continuation.resume(it.toObject(Sticker::class.java)!!)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            } catch (e: Throwable){
                continuation.resumeWithException(e)
            }
        }



    override fun subscribeToAllStickers(): LiveData<Result> =
        MutableLiveData<Result>().apply {
            try {
                getUserStickersCollection().addSnapshotListener{snapshot, e->
                    value = e?.let { throw it }
                        ?: snapshot?.let {
                            val stickers = it.documents.map {
                                it.toObject(Sticker::class.java)}
                            Result.Success(stickers)
                        }
                }
            }catch (e: Throwable){
                value = Result.Error(e)
            }
        }

    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            value = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        }

    override fun deleteSticker(stickerId: String): LiveData<Result> =
    MutableLiveData<Result>().apply {
        getUserStickersCollection().document(stickerId).delete()
            .addOnSuccessListener {
                value = Result.Success(null)
            }.addOnFailureListener{
                value = Result.Error(it)
            }
    }
}