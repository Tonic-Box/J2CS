namespace java.net
{
    public class InetAddress : global::java.lang.Object
    {
        internal readonly string host;
        internal readonly string addr;

        internal InetAddress(string host, string addr) : base(global::java.lang.RawNew.I)
        {
            this.host = host;
            this.addr = addr;
        }

        public InetAddress(global::java.lang.RawNew r) : base(r) { host = ""; addr = ""; }

        public static InetAddress getByName(global::java.lang.String h)
        {
            string hv = h == null ? "localhost" : h.Value;
            string a;
            if (hv == "localhost" || hv == "127.0.0.1")
            {
                a = "127.0.0.1";
            }
            else
            {
                try { a = global::System.Net.Dns.GetHostAddresses(hv)[0].ToString(); }
                catch (global::System.Exception) { a = hv; }
            }
            return new InetAddress(hv, a);
        }

        public static InetAddress getLoopbackAddress() { return new InetAddress("localhost", "127.0.0.1"); }
        public static InetAddress getLocalHost() { return new InetAddress("localhost", "127.0.0.1"); }

        public global::java.lang.String getHostAddress() { return global::java.lang.String.Wrap(addr); }
        public global::java.lang.String getHostName() { return global::java.lang.String.Wrap(host); }
        public int isLoopbackAddress() { return addr == "127.0.0.1" || addr == "::1" ? 1 : 0; }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(host + "/" + addr);
        }
    }
}
