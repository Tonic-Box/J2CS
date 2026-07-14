namespace java.util
{
    // Reference-identity map: keys are compared with == and hashed by identity, backed by HashMap's
    // storage with the identity-aware Spread/SameKey it selects for this subclass. Iterates in table
    // order (IdentityHashMap defines no iteration order), which HashMap.ForEachEntry already gives.
    public class IdentityHashMap : HashMap
    {
        public IdentityHashMap(global::java.lang.RawNew r) : base(r)
        {
        }
    }
}
