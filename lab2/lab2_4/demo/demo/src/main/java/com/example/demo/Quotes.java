package com.example.demo;

import java.util.*;

public class Quotes {
    private static final List<Shows> shows = new ArrayList<>(
            Arrays.asList(
                    new Shows("La Casa de Papel", new ArrayList<>(
                            Arrays.asList(
                                "The most important moments are the ones that make you realise there’s no turning back. You’ve crossed a line, and you’re stuck on the other side now.",
                                "Everything can go to hell in less than a second. In moments like these, you feel death creeping in, and you know nothing will ever be the same. But you need to do whatever it takes to survive.",
                                "In this world, everything is governed by balance. There’s what you stand to gain and what you stand to lose. And when you think you’ve got nothing to lose, you become overconfident.",
                                "First time is always so special, unique. But the last time is beyond comparison. It is priceless. But people don’t normally know it is their last time.",
                                "In the end, love is a good reason for everything to fall apart.",
                                "At some point we all become hostages to something, and when you accept that, that’s when you make up your mind.",
                                "Sometimes a truce is the most important part of a war.",
                                "When someone is in love, they look through rose-tinted glasses. Everything’s wonderful. They transform into a soft teddy bear that’s smiling all the time.",
                                "There’s always happier days to remember. And the more f***** up things are, the happier those days seem to be.",
                                "We are all going to die. And I drink to that because we are still alive. Here’s to life."
                            )
                    )),
                    new Shows("The Simpsons", new ArrayList<>(
                            Arrays.asList(
                                "To alcohol! The cause of, and solution to, all of life’s problems. ",
                                "I can’t promise I’ll try, but I’ll try to try.",
                                "I was saying “Boo-urns.”",
                                "Everything’s coming up Milhouse!",
                                "Shut up, brain, or I’ll stab you with a Q-tip!",
                                "We’re here! We’re queer! We don’t want any more bears!",
                                "Well, if it isn’t my old friend, Mr. McGreg, with a leg for an arm, and an arm for a leg.",
                                "Homer, you’re as dumb as a mule and twice as ugly. If a strange man offers you a ride, I say take it!",
                                "You don’t win friends with salad."
                            )
                    ))));
    public static List<Shows> shows() {
        List<Shows> showsWithQuotes = new ArrayList<>();
        for (Shows show : shows)
            if (show.hasQuotes())
                showsWithQuotes.add(show);
        return showsWithQuotes;
    }

    public static String[] randomQuote(int id) {
        return shows.get(id).getQuote();
    }

    public static String[] randomQuote() {
        Random r = new Random();
        int id = r.nextInt(shows.size());
        return randomQuote(id);
    }
    
}
