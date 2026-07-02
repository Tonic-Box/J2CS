package com.tonic.j2cs.project;

/**
 * The single App.csproj template. NativeAOT is on by default; the self-contained fallback
 * overrides PublishAot on the publish command line instead of using a different project file.
 * NoWarn suppresses the warning classes that goto-style emitted bodies produce systematically.
 */
public final class CsprojTemplate {

    private CsprojTemplate() {
    }

    public static String csproj() {
        return "<Project Sdk=\"Microsoft.NET.Sdk\">\n"
                + "  <PropertyGroup>\n"
                + "    <OutputType>Exe</OutputType>\n"
                + "    <TargetFramework>net9.0</TargetFramework>\n"
                + "    <ImplicitUsings>disable</ImplicitUsings>\n"
                + "    <Nullable>disable</Nullable>\n"
                + "    <LangVersion>latest</LangVersion>\n"
                + "    <PublishAot>true</PublishAot>\n"
                + "    <InvariantGlobalization>true</InvariantGlobalization>\n"
                + "    <AssemblyName>App</AssemblyName>\n"
                + "    <RootNamespace></RootNamespace>\n"
                + "    <NoWarn>CS0108;CS0162;CS0164;CS0219;CS0414;CS0649;CS1717</NoWarn>\n"
                + "  </PropertyGroup>\n"
                + "</Project>\n";
    }
}
