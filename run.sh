#!/usr/bin/env bash
set -e

SRC_DIR="src/main/java"
BIN_DIR="target/classes"
LIB_DIR="."
JAVA_OPTS=${JAVA_OPTS:-""}
CLASSPATH="$BIN_DIR:$LIB_DIR/google-java-format-1.22.0-all-deps.jar"

compile_all() {
  echo "🧩 Compiling all Java sources..."
  mkdir -p "$BIN_DIR"
  find "$SRC_DIR" -name "*.java" > sources.txt
  if [ ! -s sources.txt ]; then
    echo "❌ No Java sources found."
    exit 1
  fi
  javac -cp "$CLASSPATH" -d "$BIN_DIR" $(cat sources.txt)
  rm sources.txt
  echo "✅ Compilation complete."
}

run_class() {
  local main_class="$1"; shift || true
  echo "🚀 Running ${main_class} $*"
  java -cp "$CLASSPATH" $JAVA_OPTS "$main_class" "$@"
}

run_a1_boa() { run_class "jp.onolab.assignment01.TBoaOneMax" "$@"; }
run_a1_ga()  { run_class "jp.onolab.assignment01.TUxMggOneMax" "$@"; }
run_a2_boa() { run_class "jp.onolab.assignment02.TBoaThreeDeceptive" "$@"; }
run_a2_ga()  { run_class "jp.onolab.assignment02.TUxMggThreeDeceptive" "$@"; }

run_all() {
  run_a1_boa "$@"
  run_a1_ga "$@"
  run_a2_boa "$@"
  run_a2_ga "$@"
}

clean() {
  echo "🧹 Cleaning build directory..."
  rm -rf "$BIN_DIR"/*
}

usage() {
  cat <<'EOF'
Usage: ./run.sh <command> [args...]

Commands:
  compile                 コンパイルのみ
  clean                   target/classes を削除
  a1-boa      [args...]   assignment01 BOA
  a1-ga       [args...]   assignment01 GA
  a2-boa      [args...]   assignment02 BOA
  a2-ga       [args...]   assignment02 GA
  all         [args...]   上記4つを順に実行

Examples:
  ./run.sh compile
  ./run.sh a1-boa
  ./run.sh a2-ga 100 0.8
  ./run.sh all
EOF
}

cmd="$1"; shift || true

case "$cmd" in
  compile) compile_all ;;
  clean)   clean ;;
  a1-boa)  compile_all; run_a1_boa "$@";;
  a1-ga)   compile_all; run_a1_ga "$@";;
  a2-boa)  compile_all; run_a2_boa "$@";;
  a2-ga)   compile_all; run_a2_ga "$@";;
  all)     compile_all; run_all "$@";;
  ""|-h|--help|help) usage ;;
  *) echo "❌ Unknown command: $cmd"; usage; exit 1 ;;
esac
