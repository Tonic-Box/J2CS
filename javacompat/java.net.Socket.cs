namespace java.net
{
    public class Socket : global::java.lang.Object, global::java.io.Closeable
    {
        internal global::System.Net.Sockets.TcpClient client;

        public Socket(global::java.lang.RawNew r) : base(r) { }

        internal Socket(global::System.Net.Sockets.TcpClient c) : base(global::java.lang.RawNew.I) { client = c; }

        public void __init_Ljava_lang_String_I_V(global::java.lang.String host, int port)
        {
            client = new global::System.Net.Sockets.TcpClient(host.Value, port);
        }

        public global::java.io.InputStream getInputStream()
        {
            return new global::java.lang.NativeInputStream(client.GetStream());
        }

        public global::java.io.OutputStream getOutputStream()
        {
            return new global::java.lang.NativeOutputStream(client.GetStream());
        }

        public int getPort() { return ((global::System.Net.IPEndPoint)client.Client.RemoteEndPoint).Port; }
        public int getLocalPort() { return ((global::System.Net.IPEndPoint)client.Client.LocalEndPoint).Port; }

        public void close()
        {
            if (client != null) { client.Close(); }
        }
    }
}
