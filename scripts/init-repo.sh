#!/bin/sh

echo "Configuring git hooks..."
git config core.hooksPath .githooks
echo "Git hooks installed ✔"
