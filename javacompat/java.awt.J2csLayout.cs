namespace java.awt
{
    /// <summary>
    /// Custom Avalonia panels that reproduce Swing layout-manager geometry
    /// (sizing, positions, gaps, alignment, border insets, fill) rather than
    /// approximating with stock panels. Each is created by the matching
    /// java.awt LayoutManager shim. J2csInsets carries an EmptyBorder's insets.
    /// </summary>
    internal abstract class J2csPanel : global::Avalonia.Controls.Panel
    {
        /// <summary>Tag placed on a border wrapper around a J2csPanel so BoxLayout still stretches
        /// it to fill the cross axis (a bordered Swing container fills like a bare one).</summary>
        internal static readonly object StretchTag = new object();

        /// <summary>Tag on a Box glue filler, which absorbs leftover along-axis space.</summary>
        internal static readonly object GlueTag = new object();

        internal global::Avalonia.Thickness J2csInsets;

        protected double InnerWidth(global::Avalonia.Size size)
        {
            return size.Width - J2csInsets.Left - J2csInsets.Right;
        }

        protected double InnerHeight(global::Avalonia.Size size)
        {
            return size.Height - J2csInsets.Top - J2csInsets.Bottom;
        }

        protected global::Avalonia.Size AddInsets(double width, double height)
        {
            return new global::Avalonia.Size(width + J2csInsets.Left + J2csInsets.Right,
                    height + J2csInsets.Top + J2csInsets.Bottom);
        }
    }

    internal sealed class J2csFlowPanel : J2csPanel
    {
        internal int Align = 1;
        internal int Hgap = 5;
        internal int Vgap = 5;

        private System.Collections.Generic.List<System.Collections.Generic.List<global::Avalonia.Controls.Control>> Rows(double width)
        {
            var rows = new System.Collections.Generic.List<System.Collections.Generic.List<global::Avalonia.Controls.Control>>();
            var current = new System.Collections.Generic.List<global::Avalonia.Controls.Control>();
            double x = Hgap;
            foreach (var child in Children)
            {
                double cw = child.DesiredSize.Width;
                if (current.Count > 0 && x + cw + Hgap > width)
                {
                    rows.Add(current);
                    current = new System.Collections.Generic.List<global::Avalonia.Controls.Control>();
                    x = Hgap;
                }
                current.Add(child);
                x += cw + Hgap;
            }
            if (current.Count > 0)
            {
                rows.Add(current);
            }
            return rows;
        }

        protected override global::Avalonia.Size MeasureOverride(global::Avalonia.Size availableSize)
        {
            foreach (var child in Children)
            {
                child.Measure(global::Avalonia.Size.Infinity);
            }
            double avail = double.IsInfinity(availableSize.Width) ? double.PositiveInfinity : InnerWidth(availableSize);
            double totalW = 0;
            double totalH = Vgap;
            foreach (var row in Rows(avail))
            {
                double rowW = Hgap;
                double rowH = 0;
                foreach (var c in row)
                {
                    rowW += c.DesiredSize.Width + Hgap;
                    rowH = System.Math.Max(rowH, c.DesiredSize.Height);
                }
                totalW = System.Math.Max(totalW, rowW);
                totalH += rowH + Vgap;
            }
            if (double.IsInfinity(totalW))
            {
                totalW = 0;
            }
            return AddInsets(totalW, totalH);
        }

        protected override global::Avalonia.Size ArrangeOverride(global::Avalonia.Size finalSize)
        {
            double left = J2csInsets.Left;
            double width = InnerWidth(finalSize);
            double y = J2csInsets.Top + Vgap;
            foreach (var row in Rows(width))
            {
                double rowW = Hgap;
                double rowH = 0;
                foreach (var c in row)
                {
                    rowW += c.DesiredSize.Width + Hgap;
                    rowH = System.Math.Max(rowH, c.DesiredSize.Height);
                }
                double startX = Align == 0 ? Hgap
                        : Align == 2 ? (width - rowW + Hgap)
                        : (width - rowW) / 2 + Hgap;
                if (startX < Hgap)
                {
                    startX = Hgap;
                }
                double cx = left + startX;
                foreach (var c in row)
                {
                    var d = c.DesiredSize;
                    c.Arrange(new global::Avalonia.Rect(cx, y + (rowH - d.Height) / 2, d.Width, d.Height));
                    cx += d.Width + Hgap;
                }
                y += rowH + Vgap;
            }
            return finalSize;
        }
    }

    internal sealed class J2csBorderPanel : J2csPanel
    {
        internal int Hgap;
        internal int Vgap;
        private readonly System.Collections.Generic.Dictionary<global::Avalonia.Controls.Control, string> regions =
                new System.Collections.Generic.Dictionary<global::Avalonia.Controls.Control, string>();

        internal void SetRegion(global::Avalonia.Controls.Control child, string region)
        {
            regions[child] = region;
        }

        private global::Avalonia.Controls.Control ByRegion(string region)
        {
            foreach (var child in Children)
            {
                if (regions.TryGetValue(child, out var r) && r == region)
                {
                    return child;
                }
            }
            return null;
        }

        protected override global::Avalonia.Size MeasureOverride(global::Avalonia.Size availableSize)
        {
            foreach (var child in Children)
            {
                child.Measure(global::Avalonia.Size.Infinity);
            }
            var n = ByRegion("North");
            var s = ByRegion("South");
            var w = ByRegion("West");
            var e = ByRegion("East");
            var c = ByRegion("Center");
            double D(global::Avalonia.Controls.Control x, bool h) => x == null ? 0 : (h ? x.DesiredSize.Height : x.DesiredSize.Width);
            double midW = D(w, false) + D(e, false) + D(c, false)
                    + (w != null ? Hgap : 0) + (e != null ? Hgap : 0);
            double width = System.Math.Max(System.Math.Max(D(n, false), D(s, false)), midW);
            double midH = System.Math.Max(System.Math.Max(D(w, true), D(e, true)), D(c, true));
            double height = D(n, true) + D(s, true) + midH
                    + (n != null ? Vgap : 0) + (s != null ? Vgap : 0);
            return AddInsets(width, height);
        }

        protected override global::Avalonia.Size ArrangeOverride(global::Avalonia.Size finalSize)
        {
            double x0 = J2csInsets.Left;
            double y0 = J2csInsets.Top;
            double x1 = finalSize.Width - J2csInsets.Right;
            double y1 = finalSize.Height - J2csInsets.Bottom;
            var n = ByRegion("North");
            var s = ByRegion("South");
            var w = ByRegion("West");
            var e = ByRegion("East");
            var c = ByRegion("Center");
            if (n != null)
            {
                double h = n.DesiredSize.Height;
                n.Arrange(new global::Avalonia.Rect(x0, y0, System.Math.Max(0, x1 - x0), h));
                y0 += h + Vgap;
            }
            if (s != null)
            {
                double h = s.DesiredSize.Height;
                s.Arrange(new global::Avalonia.Rect(x0, y1 - h, System.Math.Max(0, x1 - x0), h));
                y1 -= h + Vgap;
            }
            if (w != null)
            {
                double wd = w.DesiredSize.Width;
                w.Arrange(new global::Avalonia.Rect(x0, y0, wd, System.Math.Max(0, y1 - y0)));
                x0 += wd + Hgap;
            }
            if (e != null)
            {
                double wd = e.DesiredSize.Width;
                e.Arrange(new global::Avalonia.Rect(x1 - wd, y0, wd, System.Math.Max(0, y1 - y0)));
                x1 -= wd + Hgap;
            }
            if (c != null)
            {
                c.Arrange(new global::Avalonia.Rect(x0, y0, System.Math.Max(0, x1 - x0), System.Math.Max(0, y1 - y0)));
            }
            return finalSize;
        }
    }

    internal sealed class J2csCardPanel : J2csPanel
    {
        private readonly System.Collections.Generic.Dictionary<global::Avalonia.Controls.Control, string> cardNames =
                new System.Collections.Generic.Dictionary<global::Avalonia.Controls.Control, string>();
        private int current;

        internal void AddCard(string name, global::Avalonia.Controls.Control child)
        {
            cardNames[child] = name ?? "";
            UpdateVisibility();
        }

        internal void ShowCard(string name)
        {
            for (int i = 0; i < Children.Count; i++)
            {
                if (cardNames.TryGetValue(Children[i], out var n) && n == name)
                {
                    current = i;
                    break;
                }
            }
            UpdateVisibility();
        }

        internal void Step(int delta)
        {
            if (Children.Count > 0)
            {
                current = ((current + delta) % Children.Count + Children.Count) % Children.Count;
                UpdateVisibility();
            }
        }

        internal void Edge(bool last)
        {
            current = last ? Children.Count - 1 : 0;
            UpdateVisibility();
        }

        private void UpdateVisibility()
        {
            for (int i = 0; i < Children.Count; i++)
            {
                Children[i].IsVisible = i == current;
            }
        }

        protected override global::Avalonia.Size MeasureOverride(global::Avalonia.Size availableSize)
        {
            double w = 0;
            double h = 0;
            foreach (var child in Children)
            {
                child.Measure(global::Avalonia.Size.Infinity);
                w = System.Math.Max(w, child.DesiredSize.Width);
                h = System.Math.Max(h, child.DesiredSize.Height);
            }
            return AddInsets(w, h);
        }

        protected override global::Avalonia.Size ArrangeOverride(global::Avalonia.Size finalSize)
        {
            var rect = new global::Avalonia.Rect(J2csInsets.Left, J2csInsets.Top,
                    System.Math.Max(0, InnerWidth(finalSize)),
                    System.Math.Max(0, InnerHeight(finalSize)));
            foreach (var child in Children)
            {
                child.Arrange(rect);
            }
            return finalSize;
        }
    }

    internal sealed class J2csGridBagPanel : J2csPanel
    {
        private readonly System.Collections.Generic.Dictionary<global::Avalonia.Controls.Control, global::java.awt.GridBagConstraints> cons =
                new System.Collections.Generic.Dictionary<global::Avalonia.Controls.Control, global::java.awt.GridBagConstraints>();

        internal void SetConstraints(global::Avalonia.Controls.Control child, global::java.awt.GridBagConstraints gbc)
        {
            var copy = new global::java.awt.GridBagConstraints(global::java.lang.RawNew.I);
            copy.gridx = gbc.gridx;
            copy.gridy = gbc.gridy;
            copy.gridwidth = gbc.gridwidth;
            copy.gridheight = gbc.gridheight;
            copy.weightx = gbc.weightx;
            copy.weighty = gbc.weighty;
            copy.anchor = gbc.anchor;
            copy.fill = gbc.fill;
            copy.ipadx = gbc.ipadx;
            copy.ipady = gbc.ipady;
            copy.insets = gbc.insets;
            cons[child] = copy;
        }

        private global::java.awt.GridBagConstraints Gbc(global::Avalonia.Controls.Control child)
        {
            if (cons.TryGetValue(child, out var g) && g != null)
            {
                return g;
            }
            var d = new global::java.awt.GridBagConstraints(global::java.lang.RawNew.I);
            d.__init__V();
            return d;
        }

        private static double InsL(global::java.awt.GridBagConstraints g) => g.insets == null ? 0 : g.insets.left;
        private static double InsR(global::java.awt.GridBagConstraints g) => g.insets == null ? 0 : g.insets.right;
        private static double InsT(global::java.awt.GridBagConstraints g) => g.insets == null ? 0 : g.insets.top;
        private static double InsB(global::java.awt.GridBagConstraints g) => g.insets == null ? 0 : g.insets.bottom;

        private void ComputeGrid(out double[] colW, out double[] rowH, out double[] colWt, out double[] rowWt)
        {
            int cols = 1;
            int rows = 1;
            foreach (var child in Children)
            {
                var g = Gbc(child);
                cols = System.Math.Max(cols, g.gridx + System.Math.Max(1, g.gridwidth));
                rows = System.Math.Max(rows, g.gridy + System.Math.Max(1, g.gridheight));
            }
            colW = new double[cols];
            rowH = new double[rows];
            colWt = new double[cols];
            rowWt = new double[rows];
            foreach (var child in Children)
            {
                child.Measure(global::Avalonia.Size.Infinity);
                var g = Gbc(child);
                double pw = child.DesiredSize.Width + g.ipadx + InsL(g) + InsR(g);
                double ph = child.DesiredSize.Height + g.ipady + InsT(g) + InsB(g);
                if (System.Math.Max(1, g.gridwidth) == 1)
                {
                    colW[g.gridx] = System.Math.Max(colW[g.gridx], pw);
                    colWt[g.gridx] = System.Math.Max(colWt[g.gridx], g.weightx);
                }
                if (System.Math.Max(1, g.gridheight) == 1)
                {
                    rowH[g.gridy] = System.Math.Max(rowH[g.gridy], ph);
                    rowWt[g.gridy] = System.Math.Max(rowWt[g.gridy], g.weighty);
                }
            }
            foreach (var child in Children)
            {
                var g = Gbc(child);
                int gw = System.Math.Max(1, g.gridwidth);
                int gh = System.Math.Max(1, g.gridheight);
                if (gw > 1)
                {
                    double sum = 0;
                    for (int c = g.gridx; c < g.gridx + gw; c++)
                    {
                        sum += colW[c];
                    }
                    double need = child.DesiredSize.Width + g.ipadx + InsL(g) + InsR(g);
                    if (need > sum)
                    {
                        double add = (need - sum) / gw;
                        for (int c = g.gridx; c < g.gridx + gw; c++)
                        {
                            colW[c] += add;
                        }
                    }
                }
                if (gh > 1)
                {
                    double sum = 0;
                    for (int r = g.gridy; r < g.gridy + gh; r++)
                    {
                        sum += rowH[r];
                    }
                    double need = child.DesiredSize.Height + g.ipady + InsT(g) + InsB(g);
                    if (need > sum)
                    {
                        double add = (need - sum) / gh;
                        for (int r = g.gridy; r < g.gridy + gh; r++)
                        {
                            rowH[r] += add;
                        }
                    }
                }
            }
        }

        protected override global::Avalonia.Size MeasureOverride(global::Avalonia.Size availableSize)
        {
            ComputeGrid(out var colW, out var rowH, out _, out _);
            double w = 0;
            double h = 0;
            foreach (var c in colW)
            {
                w += c;
            }
            foreach (var r in rowH)
            {
                h += r;
            }
            return AddInsets(w, h);
        }

        protected override global::Avalonia.Size ArrangeOverride(global::Avalonia.Size finalSize)
        {
            ComputeGrid(out var colW, out var rowH, out var colWt, out var rowWt);
            double baseW = 0;
            double baseH = 0;
            double sumCw = 0;
            double sumRw = 0;
            foreach (var c in colW) { baseW += c; }
            foreach (var r in rowH) { baseH += r; }
            foreach (var wt in colWt) { sumCw += wt; }
            foreach (var wt in rowWt) { sumRw += wt; }
            double innerW = InnerWidth(finalSize);
            double innerH = InnerHeight(finalSize);
            double extraW = innerW - baseW;
            double extraH = innerH - baseH;
            if (sumCw > 0)
            {
                for (int c = 0; c < colW.Length; c++) { colW[c] += extraW * colWt[c] / sumCw; }
            }
            if (sumRw > 0)
            {
                for (int r = 0; r < rowH.Length; r++) { rowH[r] += extraH * rowWt[r] / sumRw; }
            }
            double startX = J2csInsets.Left + (sumCw > 0 ? 0 : System.Math.Max(0, extraW / 2));
            double startY = J2csInsets.Top + (sumRw > 0 ? 0 : System.Math.Max(0, extraH / 2));
            double[] cx = new double[colW.Length + 1];
            double[] cy = new double[rowH.Length + 1];
            cx[0] = startX;
            for (int c = 0; c < colW.Length; c++) { cx[c + 1] = cx[c] + colW[c]; }
            cy[0] = startY;
            for (int r = 0; r < rowH.Length; r++) { cy[r + 1] = cy[r] + rowH[r]; }
            foreach (var child in Children)
            {
                var g = Gbc(child);
                int gw = System.Math.Max(1, g.gridwidth);
                int gh = System.Math.Max(1, g.gridheight);
                double cellX = cx[g.gridx] + InsL(g);
                double cellY = cy[g.gridy] + InsT(g);
                double cellW = cx[g.gridx + gw] - cx[g.gridx] - InsL(g) - InsR(g);
                double cellH = cy[g.gridy + gh] - cy[g.gridy] - InsT(g) - InsB(g);
                double dw = child.DesiredSize.Width + g.ipadx;
                double dh = child.DesiredSize.Height + g.ipady;
                double fw = g.fill == 1 || g.fill == 2 ? cellW : System.Math.Min(dw, cellW);
                double fh = g.fill == 1 || g.fill == 3 ? cellH : System.Math.Min(dh, cellH);
                double ax = cellX + AnchorX(g.anchor, cellW - fw);
                double ay = cellY + AnchorY(g.anchor, cellH - fh);
                child.Arrange(new global::Avalonia.Rect(ax, ay, System.Math.Max(0, fw), System.Math.Max(0, fh)));
            }
            return finalSize;
        }

        private static double AnchorX(int anchor, double slack)
        {
            if (anchor == 17 || anchor == 18 || anchor == 16) { return 0; }
            if (anchor == 13 || anchor == 12 || anchor == 14) { return slack; }
            return slack / 2;
        }

        private static double AnchorY(int anchor, double slack)
        {
            if (anchor == 11 || anchor == 18 || anchor == 12) { return 0; }
            if (anchor == 15 || anchor == 16 || anchor == 14) { return slack; }
            return slack / 2;
        }
    }

    internal sealed class J2csGridPanel : J2csPanel
    {
        internal int Rows = 1;
        internal int Cols;
        internal int Hgap;
        internal int Vgap;

        private void Dims(out int nrows, out int ncols)
        {
            int n = Children.Count;
            if (n == 0)
            {
                n = 1;
            }
            nrows = Rows;
            ncols = Cols;
            if (nrows > 0)
            {
                ncols = (n + nrows - 1) / nrows;
            }
            else
            {
                nrows = (n + ncols - 1) / ncols;
            }
            if (nrows < 1)
            {
                nrows = 1;
            }
            if (ncols < 1)
            {
                ncols = 1;
            }
        }

        protected override global::Avalonia.Size MeasureOverride(global::Avalonia.Size availableSize)
        {
            double cw = 0;
            double ch = 0;
            foreach (var child in Children)
            {
                child.Measure(global::Avalonia.Size.Infinity);
                cw = System.Math.Max(cw, child.DesiredSize.Width);
                ch = System.Math.Max(ch, child.DesiredSize.Height);
            }
            Dims(out int nrows, out int ncols);
            return AddInsets(ncols * cw + (ncols - 1) * Hgap, nrows * ch + (nrows - 1) * Vgap);
        }

        protected override global::Avalonia.Size ArrangeOverride(global::Avalonia.Size finalSize)
        {
            Dims(out int nrows, out int ncols);
            double innerW = InnerWidth(finalSize);
            double innerH = InnerHeight(finalSize);
            double cw = (innerW - (ncols - 1) * Hgap) / ncols;
            double ch = (innerH - (nrows - 1) * Vgap) / nrows;
            int i = 0;
            foreach (var child in Children)
            {
                int r = i / ncols;
                int c = i % ncols;
                double x = J2csInsets.Left + c * (cw + Hgap);
                double y = J2csInsets.Top + r * (ch + Vgap);
                child.Arrange(new global::Avalonia.Rect(x, y, System.Math.Max(0, cw), System.Math.Max(0, ch)));
                i++;
            }
            return finalSize;
        }
    }

    internal sealed class J2csBoxPanel : J2csPanel
    {
        internal int Axis = 1;

        protected override global::Avalonia.Size MeasureOverride(global::Avalonia.Size availableSize)
        {
            foreach (var child in Children)
            {
                child.Measure(global::Avalonia.Size.Infinity);
            }
            double along = 0;
            double cross = 0;
            foreach (var child in Children)
            {
                var d = child.DesiredSize;
                if (Axis == 1)
                {
                    along += d.Height;
                    cross = System.Math.Max(cross, d.Width);
                }
                else
                {
                    along += d.Width;
                    cross = System.Math.Max(cross, d.Height);
                }
            }
            double w = Axis == 1 ? cross : along;
            double h = Axis == 1 ? along : cross;
            return AddInsets(w, h);
        }

        private static bool CrossStretch(global::Avalonia.Controls.Control child)
        {
            return child is J2csPanel || ReferenceEquals(child.Tag, J2csPanel.StretchTag);
        }

        // Along-axis growth: glue and content panels (BorderLayout/BoxLayout/GridBag, incl. their
        // bordered wrappers) share leftover space like Swing; a FlowLayout panel (e.g. a button row)
        // hugs its content row and stays at preferred size, so trailing button bars sit at the edge.
        private static bool AlongFill(global::Avalonia.Controls.Control child)
        {
            return ReferenceEquals(child.Tag, J2csPanel.GlueTag)
                    || ReferenceEquals(child.Tag, J2csPanel.StretchTag)
                    || (child is J2csPanel && !(child is J2csFlowPanel));
        }

        protected override global::Avalonia.Size ArrangeOverride(global::Avalonia.Size finalSize)
        {
            double innerW = InnerWidth(finalSize);
            double innerH = InnerHeight(finalSize);
            double alongAvail = Axis == 1 ? innerH : innerW;
            double sumAlong = 0;
            int fillCount = 0;
            foreach (var child in Children)
            {
                var d = child.DesiredSize;
                sumAlong += Axis == 1 ? d.Height : d.Width;
                if (AlongFill(child))
                {
                    fillCount++;
                }
            }
            double extra = alongAvail - sumAlong;
            double perFill = extra > 0 && fillCount > 0 ? extra / fillCount : 0;
            double pos = Axis == 1 ? J2csInsets.Top : J2csInsets.Left;
            foreach (var child in Children)
            {
                var d = child.DesiredSize;
                bool grow = AlongFill(child);
                bool cross = CrossStretch(child);
                if (Axis == 1)
                {
                    double h = d.Height + (grow ? perFill : 0);
                    double cw = cross ? innerW : System.Math.Min(d.Width, innerW);
                    double x = J2csInsets.Left + (cross ? 0 : (innerW - cw) / 2);
                    child.Arrange(new global::Avalonia.Rect(x, pos, cw, h));
                    pos += h;
                }
                else
                {
                    double w = d.Width + (grow ? perFill : 0);
                    double ch = cross ? innerH : System.Math.Min(d.Height, innerH);
                    double y = J2csInsets.Top + (cross ? 0 : (innerH - ch) / 2);
                    child.Arrange(new global::Avalonia.Rect(pos, y, w, ch));
                    pos += w;
                }
            }
            return finalSize;
        }
    }
}
