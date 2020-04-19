package com.bankmtk.neuromemory.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bankmtk.neuromemory.data.errors.NoAuthException
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.provider.FireStoreProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.nhaarman.mockitokotlin2.mock
import io.mockk.MockK
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.slot
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    @Before
    fun setUp(){
        clearMocks(mockCollection,mockDocument1, mockDocument2, mockDocument3)

        every { mockAuth.currentUser } returns  mockUser
        every { mockUser.uid } returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockCollection
        every { mockDocument1.toObject(Sticker::class.java) } returns testStickers[0]
        every { mockDocument2.toObject(Sticker::class.java) } returns testStickers[1]
        every { mockDocument3.toObject(Sticker::class.java) } returns testStickers[2]
    }
    @Test
    fun `should throw if no auth`(){
        var result: Any? = null
        every { mockAuth.currentUser } returns null
        provider.subscribeToAllStickers().observeForever{
            result = (it as? Result.Error)?.error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `subscribeAllStickers return stickers`(){
        var result:List<Sticker> = null
        var slot =  slot<EventListener<QuerySnapshot>>()
        val mockSnapshot= mock<QuerySnapshot>()

    }

}