SpellWrecker
============

An evil spellchecker designed for a managed code Java virtual machine rootkit.  Both SpellWrecker implementations insert more typos the faster you type.  As you slow down to correct your spelling mistakes the SpellWrecker begins to behave benignly.  It's goal is to decrease productivity.

**Implementation 1:** Using a Markov chain, this implementation corrupts the input character by predicting the next character.  More often than not the prediction will be wrong, but similar to the observed training input.  This implementation has a larger memory footprint than implementation 2.  The memory footprint grows with the size of the length Markov Chain, but the typos produced are more realistic with longer chains.

**Implementation 2:** Picks a suitable typo based on keys that are physically located near each other on a QWERTY keyboard.  This implementation has a very low footprint and would be an ideal candidate for inserting as a managed code rootkit payload.

To test the SpellWrecker in it's default configuration double click on the SpellWrecker.jar executable or run `java -jar TestSpellWrecker.jar` from the command line.

![SpellWrecker Derbycon Demo](./SpellWrecker.gif)

For more details on managed code rootkits, check out [Managed Code Rootkits: Hooking into Runtime Environments](http://www.amazon.com/gp/product/1597495743/ref=as_li_tl?ie=UTF8&camp=1789&creative=390957&creativeASIN=1597495743&linkCode=as2&tag=zombiest-20&linkId=OCT7HWLGKCCU6QYG).