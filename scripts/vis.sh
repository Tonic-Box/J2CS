#!/usr/bin/env bash
# Visual-fidelity loop for one scenario.
#   scripts/vis.sh <ScenarioClass>
# Renders the JVM Metal reference and the transpiled Avalonia candidate at the same size,
# then diffs geometry/colors. Artifacts land in build/visual/<Scenario>/ (ref.png, cand.png,
# ref.txt, cand.txt, diff.txt).
set -u
S="$1"
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"
OUT="build/visual/$S"
JAR="build/visual/$S/$S.jar"
REFBIN="build/visual/_refbin"
rm -rf "$OUT"; mkdir -p "$OUT" "$REFBIN"

JAVAC="/c/Program Files/Java/jdk-21/bin/javac.exe"
JAVA="/c/Program Files/Java/jdk-21/bin/java.exe"
JAR_TOOL="/c/Program Files/Java/jdk-21/bin/jar.exe"

echo "== compile reference harness + scenario =="
"$JAVAC" -d "$REFBIN" \
  src/test/java/com/tonic/j2cs/visual/VisualRef.java \
  src/test/java/com/tonic/j2cs/visual/VisualDiff.java \
  "src/test/resources/visfixtures/$S/$S.java" || exit 1

echo "== JVM reference render =="
SIZE=$("$JAVA" -cp "$REFBIN" com.tonic.j2cs.visual.VisualRef "$S" "$OUT" | grep '^SIZE=' | cut -d= -f2)
echo "ref size: $SIZE"

echo "== jar + transpile scenario =="
(cd "$REFBIN" && "$JAR_TOOL" cfe "$ROOT/$JAR" "$S" "$S.class")
rm -rf "$OUT/App"
./gradlew.bat -q run --args="$JAR --out $OUT/App --no-build" 2>&1 | tail -1

echo "== build candidate driver =="
mkdir -p "$OUT/App/Driver"
cp src/test/resources/visharness/Driver.cs "$OUT/App/Driver/"
cp src/test/resources/visharness/Driver.csproj "$OUT/App/Driver/"
(cd "$OUT/App/Driver" && dotnet build -c Release --nologo 2>&1 | grep -E 'error|Build succeeded' | head)

echo "== candidate render =="
DLL="$OUT/App/Driver/bin/Release/net9.0/Driver.dll"
VIS_DIR="$OUT" VIS_SCENARIO="jdefault.$S" VIS_SIZE="$SIZE" timeout 60 dotnet "$DLL" 2>&1 | head

echo "== diff =="
"$JAVA" -cp "$REFBIN" com.tonic.j2cs.visual.VisualDiff "$OUT"
