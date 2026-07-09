namespace java.util.concurrent
{
    public class CyclicBarrier : global::java.lang.Object
    {
        private global::System.Threading.Barrier b;

        public CyclicBarrier(global::java.lang.RawNew r) : base(r) { }
        public void __init_I_V(int parties) { b = new global::System.Threading.Barrier(parties); }

        public void __init_ILjava_lang_Runnable__V(int parties, global::java.lang.Runnable barrierAction)
        {
            b = new global::System.Threading.Barrier(parties, _ => barrierAction.run());
        }

        public int await()
        {
            int idx = b.ParticipantsRemaining - 1;
            b.SignalAndWait();
            return idx;
        }

        public int getParties() { return b.ParticipantCount; }
        public int getNumberWaiting() { return b.ParticipantCount - b.ParticipantsRemaining; }
    }
}
