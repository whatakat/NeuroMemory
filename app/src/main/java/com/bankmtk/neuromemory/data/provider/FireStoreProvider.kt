package com.bankmtk.neuromemory.data.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bankmtk.neuromemory.data.errors.NoAuthException
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.data.model.User
import com.github.ajalt.timberkt.Timber
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

    override suspend fun saveSticker(sticker: Sticker): Sticker =
    suspendCoroutine { continuation ->
        try {
            getUserStickersCollection()
                .document(sticker.id)
                .set(sticker).addOnSuccessListener {
                    Timber.d{"Sticker $sticker is saved"}
                    continuation.resume(sticker)
                }.addOnFailureListener {
                    Timber.e(it){"Error saving sticker $sticker with message: ${it.message}"}
                    continuation.resumeWithException(it)
                }
        }catch (e: Throwable){
            continuation.resumeWithException(e)
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

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        continuation.resume(currentUser?.let { User(it.displayName ?: "", it.email ?: "") })
    }

    override suspend fun deleteSticker(stickerId: String): Unit =
    suspendCoroutine { continuation ->
        getUserStickersCollection().document(stickerId).delete()
            .addOnSuccessListener {
                continuation.resume(Unit)
            }.addOnFailureListener{
                continuation.resumeWithException(it)
            }
    }
}