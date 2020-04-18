package com.bankmtk.neuromemory.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.provider.FireStoreProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule

class FireStoreProviderTest {
    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mock<FirebaseFirestore>()
    private val mockAuth = mock<FirebaseAuth>()
    private val mockCollection = mock<CollectionReference>()
    private val mockUser = mock<FirebaseUser>()
    private val mockDocument1 = mock<DocumentSnapshot>()
    private val mockDocument2 = mock<DocumentSnapshot>()
    private val mockDocument3 = mock<DocumentSnapshot>()
    private val testStickers = listOf(Sticker(id = "1"), Sticker(id = "2"), Sticker(id = "3"))
    private val provider: FireStoreProvider = FireStoreProvider(mockAuth, mockDb)

}