# About
J2CS is a Jave to C# transpiler powered by YABR.

# Note
This project is still very much a WIP. It still has its gaps and limitations, and is not yet ready for production use.

# Usage:
```
J2CS - transpiles JVM bytecode (.class/.jar) to C# and builds a native Windows exe.

usage: java -jar J2CS.jar <input.class|input.jar> -o <outDir> [options]
       java -jar J2CS.jar --bootstrap-report <fqcn>[,<fqcn>...]
       java -jar J2CS.jar --bootstrap-coverage
       java -jar J2CS.jar --help

arguments:
  <input.class|input.jar>   the class or jar to transpile (jar entry point is its Main-Class)
  -o, --out <outDir>        output directory for the generated solution and exe (required)

options:
  --main <fqcn>             entry-point class override (dotted name), e.g. com.example.App
  --no-build                emit the C# solution only; do not invoke dotnet
  --aot                     publish with NativeAOT (minimal native binary) instead of the default
  --self-contained          publish a self-contained single-file exe (the default)
  --classic-bodies          emit goto/label method bodies instead of structured control flow
  --run                     run the produced exe after publishing
  --dump-ir                 dump the lifted IR for debugging
  --bootstrap <fqcn>[,...]  generate the named JDK classes from platform bytecode (else shimmed)
  -h, --help                show this help
  --version                 print the j2cs version

By default the publish produces a self-contained single-file exe (bundled runtime,
no .NET install required). GUI (Swing/AWT) apps are rendered via Avalonia.
```