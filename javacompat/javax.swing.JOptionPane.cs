namespace javax.swing
{
    public class JOptionPane : global::java.lang.Object
    {
        public const int YES_OPTION = 0;
        public const int OK_OPTION = 0;
        public const int NO_OPTION = 1;
        public const int CANCEL_OPTION = 2;
        public const int CLOSED_OPTION = -1;
        public const int DEFAULT_OPTION = -1;
        public const int YES_NO_OPTION = 0;
        public const int YES_NO_CANCEL_OPTION = 1;
        public const int OK_CANCEL_OPTION = 2;
        public const int ERROR_MESSAGE = 0;
        public const int INFORMATION_MESSAGE = 1;
        public const int WARNING_MESSAGE = 2;
        public const int QUESTION_MESSAGE = 3;
        public const int PLAIN_MESSAGE = -1;

        private JOptionPane(global::java.lang.RawNew r) : base(r)
        {
        }

        public static int showConfirmDialog(global::java.awt.Component parent, global::java.lang.Object message,
                global::java.lang.String title, int optionType)
        {
            var win = new global::Avalonia.Controls.Window
            {
                Title = global::java.lang.JRuntime.Cs(title),
                Width = 340,
                Height = 160,
                WindowStartupLocation = global::Avalonia.Controls.WindowStartupLocation.CenterScreen
            };
            var stack = new global::Avalonia.Controls.StackPanel { Margin = new global::Avalonia.Thickness(16) };
            stack.Children.Add(new global::Avalonia.Controls.TextBlock
            {
                Text = global::java.lang.JRuntime.Str(message)
            });
            var buttons = new global::Avalonia.Controls.StackPanel
            {
                Orientation = global::Avalonia.Layout.Orientation.Horizontal,
                HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Center,
                Margin = new global::Avalonia.Thickness(0, 12, 0, 0)
            };
            var yes = new global::Avalonia.Controls.Button { Content = "Yes", Margin = new global::Avalonia.Thickness(4, 0, 4, 0) };
            var no = new global::Avalonia.Controls.Button { Content = "No", Margin = new global::Avalonia.Thickness(4, 0, 4, 0) };
            buttons.Children.Add(yes);
            buttons.Children.Add(no);
            stack.Children.Add(buttons);
            win.Content = stack;
            int result = CLOSED_OPTION;
            var frame = new global::Avalonia.Threading.DispatcherFrame();
            yes.Click += (s, e) => { result = YES_OPTION; win.Close(); };
            no.Click += (s, e) => { result = NO_OPTION; win.Close(); };
            win.Closed += (s, e) => frame.Continue = false;
            win.Show();
            global::Avalonia.Threading.Dispatcher.UIThread.PushFrame(frame);
            return result;
        }

        public static int showConfirmDialog(global::java.awt.Component parent, global::java.lang.Object message)
        {
            return showConfirmDialog(parent, message, global::java.lang.String.Wrap("Select an Option"), YES_NO_CANCEL_OPTION);
        }

        public static void showMessageDialog(global::java.awt.Component parent, global::java.lang.Object message,
                global::java.lang.String title, int messageType)
        {
            var win = new global::Avalonia.Controls.Window
            {
                Title = global::java.lang.JRuntime.Cs(title),
                Width = 340,
                Height = 150,
                WindowStartupLocation = global::Avalonia.Controls.WindowStartupLocation.CenterScreen
            };
            var stack = new global::Avalonia.Controls.StackPanel
            {
                Margin = new global::Avalonia.Thickness(16)
            };
            stack.Children.Add(new global::Avalonia.Controls.TextBlock
            {
                Text = global::java.lang.JRuntime.Str(message)
            });
            var ok = new global::Avalonia.Controls.Button
            {
                Content = "OK",
                HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Center,
                Margin = new global::Avalonia.Thickness(0, 12, 0, 0)
            };
            stack.Children.Add(ok);
            win.Content = stack;
            var frame = new global::Avalonia.Threading.DispatcherFrame();
            ok.Click += (sender, e) => win.Close();
            win.Closed += (sender, e) => frame.Continue = false;
            win.Show();
            global::Avalonia.Threading.Dispatcher.UIThread.PushFrame(frame);
        }
    }
}
