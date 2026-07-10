package com.tonic.j2cs.project;

/**
 * The single App.csproj template. The default self-contained publish overrides PublishAot on
 * the command line rather than using a different project file. NoWarn suppresses the warning
 * classes that goto-style emitted bodies produce systematically.
 */
public final class CsprojTemplate {

    private CsprojTemplate() {
    }

    public static final String TARGET_FRAMEWORK = "net9.0";

    private static final String AVALONIA_VERSION = "11.2.1";

    public static String csproj(boolean usesGui, java.util.List<String> nativeLibs) {
        return "<Project Sdk=\"Microsoft.NET.Sdk\">\n"
                + "  <PropertyGroup>\n"
                + "    <OutputType>Exe</OutputType>\n"
                + "    <TargetFramework>" + TARGET_FRAMEWORK + "</TargetFramework>\n"
                + "    <ImplicitUsings>disable</ImplicitUsings>\n"
                + "    <Nullable>disable</Nullable>\n"
                + "    <LangVersion>latest</LangVersion>\n"
                + "    <AllowUnsafeBlocks>true</AllowUnsafeBlocks>\n"
                + "    <PublishAot>" + (usesGui ? "false" : "true") + "</PublishAot>\n"
                + "    <InvariantGlobalization>true</InvariantGlobalization>\n"
                + "    <AssemblyName>App</AssemblyName>\n"
                + "    <RootNamespace></RootNamespace>\n"
                + "    <NoWarn>CS0108;CS0162;CS0164;CS0219;CS0414;CS0649;CS1717</NoWarn>\n"
                + "  </PropertyGroup>\n"
                + (usesGui ? avaloniaPackages() : "")
                + nativeContent(nativeLibs)
                + "</Project>\n";
    }

    private static String nativeContent(java.util.List<String> nativeLibs) {
        if (nativeLibs == null || nativeLibs.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("  <ItemGroup>\n");
        for (String relPath : nativeLibs) {
            String win = relPath.replace('/', '\\');
            sb.append("    <Content Include=\"").append(win).append("\">\n")
                    .append("      <CopyToOutputDirectory>PreserveNewest</CopyToOutputDirectory>\n")
                    .append("      <CopyToPublishDirectory>PreserveNewest</CopyToPublishDirectory>\n")
                    .append("      <ExcludeFromSingleFile>true</ExcludeFromSingleFile>\n")
                    .append("    </Content>\n");
        }
        sb.append("  </ItemGroup>\n");
        return sb.toString();
    }

    private static String avaloniaPackages() {
        return "  <ItemGroup>\n"
                + "    <PackageReference Include=\"Avalonia\" Version=\"" + AVALONIA_VERSION + "\" />\n"
                + "    <PackageReference Include=\"Avalonia.Desktop\" Version=\"" + AVALONIA_VERSION + "\" />\n"
                + "    <PackageReference Include=\"Avalonia.Themes.Fluent\" Version=\"" + AVALONIA_VERSION + "\" />\n"
                + "  </ItemGroup>\n";
    }
}
