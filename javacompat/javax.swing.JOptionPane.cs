namespace javax.swing
{
    public class JOptionPane : global::java.lang.Object
    {
        private JOptionPane(global::java.lang.RawNew r) : base(r)
        {
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
