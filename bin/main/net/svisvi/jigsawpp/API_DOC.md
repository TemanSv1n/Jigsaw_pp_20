**API DOC**

Here you can read about some methods and other stuff, which should be called in an inapropriate way.

**Special Effect giving, Purgative, Poop:**

These effects (if you want condition checkers and lots of fun) should be granted through special methods inside of them.
For example

*/effects/PoopEffect#addEffectGasWay()* 

will grant effect, checking for gas mask and do some stuff to it, if coded enough


**Purgen pilules ++effect and durationBuff**:

duration buff you grant to an itemstack through

*/item/pilule/AbstractPiluleItem#setDurationBuff()*
*/item/pilule/AbstractPiluleItem#setPurity()*

additional effect you grant through 

*PotionUtils#setCustomEffects*

Actual documentation for pilules you can see in the MossElephantThumb class

**Purgen bundle**
get (and remove) first item from bundle
PurgenBundleItem#useFirst()



! Purgen pilules should be added to dispenser manually
pilule/PiluleItemExtensions

**INTERFACE FOR ARM POSING**
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;

**ModDatas**
This is a class for storing some arrays or dicts with objects, because im too lazy to use JSON here.
Who wants - will add this thing
You should add this by ModDatas.*add() in SetupEvent
