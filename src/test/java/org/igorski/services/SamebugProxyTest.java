package org.igorski.services;

import org.igorski.clients.SamebugClient;
import org.igorski.model.SamebugRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SamebugProxyTest {
    @Mock
    private ExceptionRequestFactory requestFactoryMock;
    @Mock
    private Throwable throwableMock;
    @Mock
    private SamebugClientFactory samebugClientFactoryMock;
    @Mock
    private SamebugClient samebugClientMock;

    @Test
    public void shouldUseRequestFactory() {
        when(requestFactoryMock.createInstance(throwableMock)).thenReturn(getRequestStub());
        when(samebugClientFactoryMock.getInstance()).thenReturn(samebugClientMock);
        SamebugProxy proxy = new SamebugProxy(requestFactoryMock, samebugClientFactoryMock);

        proxy.getSamebugRequest(throwableMock);
        verify(requestFactoryMock).createInstance(throwableMock);
    }

    private SamebugRequest getRequestStub() {
        SamebugRequest samebugRequest = new SamebugRequest();

        samebugRequest.setTypeName("typeName");
        samebugRequest.setMessage("SomeMessage");
        samebugRequest.setFrames(Collections.emptyList());

        return samebugRequest;
    }
}