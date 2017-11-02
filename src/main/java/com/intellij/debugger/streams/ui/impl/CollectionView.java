/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.debugger.streams.ui.impl;

import com.intellij.debugger.streams.trace.TraceElement;
import com.intellij.debugger.streams.ui.TraceContainer;
import com.intellij.debugger.streams.ui.ValuesSelectionListener;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Vitaliy.Bibaev
 */
public class CollectionView extends JPanel implements TraceContainer {
  private final CollectionTree myInstancesTree;

  CollectionView(@NotNull JLabel header,
                 @NotNull CollectionTree collectionTree) {
    super(new BorderLayout());
    add(header, BorderLayout.NORTH);

    myInstancesTree = collectionTree;

    final JBScrollPane scroll = new JBScrollPane(myInstancesTree);

    add(scroll, BorderLayout.CENTER);
    Disposer.register(this, myInstancesTree);
  }

  CollectionView(@NotNull CollectionTree tree) {
    this(createDefaultLabel(tree), tree);
  }

  private static JLabel createDefaultLabel(@NotNull CollectionTree tree) {
    final JLabel label = new JBLabel(String.valueOf(tree.getItemsCount()), SwingConstants.CENTER);
    label.setForeground(JBColor.GRAY);
    final Font oldFont = label.getFont();
    label.setFont(oldFont.deriveFont(oldFont.getSize() - JBUI.scale(1.f)));
    label.setBorder(JBUI.Borders.empty(3, 0));
    return label;
  }

  @Override
  public void dispose() {
  }

  @Override
  public void highlight(@NotNull List<TraceElement> elements) {
    myInstancesTree.highlight(elements);
  }

  @Override
  public void select(@NotNull List<TraceElement> elements) {
    myInstancesTree.select(elements);
  }

  @Override
  public void addSelectionListener(@NotNull ValuesSelectionListener listener) {
    myInstancesTree.addSelectionListener(listener);
  }

  @Override
  public boolean highlightedExists() {
    return myInstancesTree.highlightedExists();
  }

  @NotNull
  protected CollectionTree getInstancesTree() {
    return myInstancesTree;
  }
}
