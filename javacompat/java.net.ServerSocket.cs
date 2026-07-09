namespace java.net
{
    public class ServerSocket : global::java.lang.Object, global::java.io.Closeable
    {
        private global::System.Net.Sockets.TcpListener listener;

        public ServerSocket(global::java.lang.RawNew r) : base(r) { }

        public void __init_I_V(int port)
        {
            listener = new global::System.Net.Sockets.TcpListener(global::System.Net.IPAddress.Loopback, port);
            listener.Start();
        }

        public int getLocalPort()
        {
            return ((global::System.Net.IPEndPoint)listener.LocalEndpoint).Port;
        }

        public Socket accept()
        {
            return new Socket(listener.AcceptTcpClient());
        }

        public void close()
        {
            if (listener != null) { listener.Stop(); }
        }
    }
}
