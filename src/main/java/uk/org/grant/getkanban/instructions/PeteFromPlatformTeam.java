package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.card.Blocker;
import uk.org.grant.getkanban.card.Card;

import java.util.Optional;

public class PeteFromPlatformTeam implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeteFromPlatformTeam.class);

    @Override
    public void execute(Board b) {
        LOGGER.info("Pete from the Platform Team needs to update a back-end systems");

        Optional<Card> s10 = b.getCards().stream().filter(c -> c.getName().equals("S10")).findFirst();
        if (s10.isPresent()) {
            s10.get().setBlocker(new Blocker());
            LOGGER.info("Blocker added to {}", s10.get());
        } else {
            LOGGER.error("Couldn't find issue S10");
        }
    }
}
