package uk.org.grant.getkanban.card;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.Day;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FixedDateCardTest {
    @Test
    public void fineForMissingAudit() {
        Card f1 = Cards.getCard("F1");
        f1.onSelected(new Context(new Board(), new Day(1)));
        assertThat(f1.getFineOrPayment(), is(-1500));
    }

    @Test
    public void f1HasFixedDate() {
        Card f1 = Cards.getCard("F1");
        assertThat(f1.getDueDate(), is(15));
    }

    @Test
    public void f2HasFixedDate() {
        Card f2 = Cards.getCard("F2");
        assertThat(f2.getDueDate(), is(21));
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(FixedDateCard.class)
                .withOnlyTheseFields("name")
                .verify();
    }
}
