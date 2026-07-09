namespace java.lang
{
    public class ProcessBuilder : global::java.lang.Object
    {
        private readonly global::System.Collections.Generic.List<string> command =
                new global::System.Collections.Generic.List<string>();
        private string workDir;

        public ProcessBuilder(global::java.lang.RawNew r) : base(r) { }

        public void __init__Ljava_lang_String__V(global::java.lang.String[] cmd)
        {
            foreach (var c in cmd) { command.Add(c == null ? "" : c.Value); }
        }

        public void __init_Ljava_util_List__V(global::java.util.List cmd)
        {
            var it = cmd.iterator();
            while (it.hasNext() != 0) { command.Add(global::java.lang.JRuntime.Str(it.next())); }
        }

        public ProcessBuilder redirectErrorStream(int redirect) { return this; }

        public ProcessBuilder directory(global::java.io.File dir)
        {
            workDir = dir == null ? null : dir.path;
            return this;
        }

        public Process start()
        {
            var psi = new global::System.Diagnostics.ProcessStartInfo
            {
                FileName = command.Count > 0 ? command[0] : "",
                RedirectStandardOutput = true,
                RedirectStandardError = true,
                RedirectStandardInput = true,
                UseShellExecute = false,
                CreateNoWindow = true
            };
            for (int i = 1; i < command.Count; i++) { psi.ArgumentList.Add(command[i]); }
            if (workDir != null) { psi.WorkingDirectory = workDir; }
            var p = new Process(global::java.lang.RawNew.I);
            p.proc = global::System.Diagnostics.Process.Start(psi);
            return p;
        }
    }
}
