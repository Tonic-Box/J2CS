namespace java.time
{
    public class ZoneId : global::java.lang.Object
    {
        internal readonly string id;

        internal ZoneId(string zoneId) : base(global::java.lang.RawNew.I) { id = zoneId; }
        public ZoneId(global::java.lang.RawNew r) : base(r) { id = "Z"; }

        public static ZoneId of(global::java.lang.String zoneId) { return new ZoneId(zoneId.Value); }
        public static ZoneId systemDefault() { return new ZoneId("UTC"); }

        public global::java.lang.String getId() { return global::java.lang.String.Wrap(id); }

        public override int equals(global::java.lang.Object o) { return o is ZoneId z && z.id == id ? 1 : 0; }
        public override int hashCode() { return id.GetHashCode(); }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(id); }
    }
}
