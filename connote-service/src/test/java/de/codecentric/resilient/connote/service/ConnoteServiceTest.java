package de.codecentric.resilient.connote.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import de.codecentric.resilient.connote.entity.Connote;
import de.codecentric.resilient.connote.repository.ConnoteRepository;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import de.codecentric.resilient.dto.ConnoteDTO;
import de.codecentric.resilient.connote.utils.RandomConnoteGenerator;

/**
 * @author Benjamin Wilms (xd98870)
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnoteServiceTest {
    @Mock
    private ConnoteRepository connoteRepositoryMock;

    @Mock
    private RandomConnoteGenerator randomConnoteGeneratorMock;

    private ConnoteService connoteService;

    @Before
    public void setUp() throws Exception {
        connoteService = new ConnoteService(connoteRepositoryMock, randomConnoteGeneratorMock);
    }

    @Test
    public void nextConnote_Goodcase() throws Exception {
        Long uniqueConnote = 1L;
        Connote connoteToBeSaved = new Connote(uniqueConnote);
        connoteToBeSaved.setCreated(new LocalDateTime(2017, 01, 02, 12, 03, 10));

        when(randomConnoteGeneratorMock.randomNumber()).thenReturn(342L, uniqueConnote);
        when(connoteRepositoryMock.findOne(342L)).thenReturn(new Connote(342L));
        when(connoteRepositoryMock.findOne(uniqueConnote)).thenReturn(null);
        when(connoteRepositoryMock.save(any(Connote.class))).thenReturn(connoteToBeSaved);

        ConnoteDTO connote = connoteService.createConnote();

        assertThat(connote.getConnote(), is(1L));

        verify(randomConnoteGeneratorMock, times(2)).randomNumber();

    }

}