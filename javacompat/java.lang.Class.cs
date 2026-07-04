namespace java.lang
{
    public class Class : Object
    {
        private readonly string name;

        public Class(RawNew r) : base(r)
        {
            name = "";
        }

        private Class(string name) : base(RawNew.I)
        {
            this.name = name;
        }

        public static Class Of(string name)
        {
            return new Class(name);
        }

        public String getName()
        {
            return String.Wrap(name);
        }

        public String getSimpleName()
        {
            int cut = 0;
            for (int i = 0; i < name.Length; i++)
            {
                if (name[i] == '.' || name[i] == '$')
                {
                    cut = i + 1;
                }
            }
            return String.Wrap(name.Substring(cut));
        }
    }
}
