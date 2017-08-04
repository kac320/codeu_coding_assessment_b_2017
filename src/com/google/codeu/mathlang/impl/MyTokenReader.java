// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.google.codeu.mathlang.core.tokens.NumberToken;
import com.google.codeu.mathlang.core.tokens.StringToken;
import com.google.codeu.mathlang.core.tokens.SymbolToken;
import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.parsing.TokenReader;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {

  private String source;
  private Queue<Token> tokens = new LinkedList<Token>();

  public MyTokenReader(String source) {
    this.source = source;
    for (int i = 0; i < source.length(); i++) {
      int temp = 0;
      if (source.charAt(i) == ' ' && i == 0) {

        if (symbol(source.charAt(i))) {
          SymbolToken st = new SymbolToken(source.charAt(i));
          tokens.add(st);
        }

        else if (alphabet(source.charAt(i))) {
          int end = 0;
          for (int j = i; j < source.length(); j++) {
            if (source.charAt(j) == ' ') {
              end = j - 1;
              j = source.length();
            }
          }
          StringToken st = new StringToken(source.substring(i, end));
          tokens.add(st);
        }

        else if (number(source.charAt(i))) {
          int end = 0;
          for (int j = i; j < source.length(); j++) {
            if (source.charAt(j) == ' ') {
              end = j - 1;
              j = source.length();
            }
          }

          int num = Integer.parseInt(source.substring(i, end));
          NumberToken nt = new NumberToken(num);
          tokens.add(nt);
        }
      }

      else if (source.charAt(i) == ' ') {
        StringToken st = new StringToken(source.substring(temp, i));
        tokens.add(st);
        temp = i + 1;
      }
    }
  }

  @Override
  public Token next() throws IOException {
    return tokens.poll();
  }

  public boolean symbol(char s) {
    return (s == '-' || s == '+' || s == '=');
  }

  public boolean alphabet(char s) {
    return (Character.isAlphabetic(s));
  }

  public boolean number(char s) {
    return (Character.isDigit(s));
  }
}
