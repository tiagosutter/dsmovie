import com.tiagoexemplo.dsmovie.common.BaseObservable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class BaseObservableTest {

    // region constants
    companion object {

    }
    // endregion constants


    // region helper fields
    @Mock
    private lateinit var listener1: MyInterface
    @Mock
    private lateinit var listener2: MyInterface
    // endregion helper fields


    private lateinit var SUT: BaseObserverTestHelper

    @Before
    @Throws(Exception::class)
    fun setup() {
        SUT = BaseObserverTestHelper()
    }

    @Test
    fun run_allRegisteredListenersNotified() {
        // Arrange
        SUT.registerListener(listener1)
        SUT.registerListener(listener2)

        // Act
        SUT.run()

        // Assert
        verify(listener1).notifyListeners()
        verify(listener2).notifyListeners()
    }

    @Test
    fun run_unregisteredListenersNotInteractedWith() {
        // Arrange
        SUT.registerListener(listener1)
        SUT.unregisterListener(listener2)

        // Act
        SUT.run()

        // Assert
        verifyNoInteractions(listener2)
    }

    // region helper methods

    // endregion helper methods


    // region helper classes
    private interface MyInterface {
        fun notifyListeners()
    }

    private class BaseObserverTestHelper : BaseObservable<MyInterface>() {
        fun run() {
            listeners.forEach { it.notifyListeners() }
        }
    }
    // endregion helper classes
}