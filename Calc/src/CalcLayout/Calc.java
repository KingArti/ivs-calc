/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalcLayout;



import java.awt.event.KeyEvent;
import IVS.IVSMath;
import IVS.IVSMathImpl;
import IVS.IVSNumber;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



/**
 * Description: Main class for Calc UI
 * @author Michael Švasta, xsvast00@stud.fit.vutbr.cz
 */
public class Calc extends javax.swing.JFrame {
    IVSMath math = new IVSMathImpl();    
    private static final IVSNumber numberNeg = new IVSNumber(-1);
    boolean isAnswer = false;      
    boolean wasAnswer = false;
    private enum Base { 
        bin(2), dec(10), hex(16);
        
        private final int value;
        private Base(int value){
            this.value = value;
        }
        
        public int getValue(){
            return value;
        }
    }
    
    Base base = Base.dec;
    boolean isCtrlActive = false;
    String tmpDisplay = "";
    
    /**
     * Stack of undo actions
     */
    private final List<String> stackUndo = new ArrayList<>();  
    /**
    * Push string to stack of undo
    * @param String String to be pushed into stack
    */
    private void pushUndo(String displayText) {
	stackUndo.add(displayText);
        pushUndoAnswer(isAnswer);
    }

    /**
     * Pops string from stack of undo
     * @return String from top of stack
     */
    private String popUndo() {
        if (stackUndo.isEmpty())
            return "";
        
        //remove top of stack
        String tmp = stackUndo.remove(stackUndo.size() - 1);
        tmpDisplay = tmp;   //save removed item
        
        //get text of display
        String tmpDisplay = txt_display.getText();
        if (tmpDisplay != popRedoNotRemove())   //display's text isn't same as top of stack
            pushRedo(tmpDisplay);
        
        isAnswer = popUndoAnswer();
        
        return tmp;
    }
    
     /**
     * Pops string from stack of undo without remove
     * @return String from top of stack
     */
    private String popUndoNotRemove() {
        if (stackUndo.isEmpty())
            return "";
        
        return stackUndo.get(stackUndo.size()-1);
    }
    
    /**
     * Stack of redo actions
     */
    private final List<String> stackRedo = new ArrayList<>();  
    /**
    * Push string to stack of redo
    * @param String String to be pushed into stack
    */
    private void pushRedo(String displayText) {
	stackRedo.add(displayText);
        pushRedoAnswer(isAnswer);
        System.out.println("pushRedo: " + displayText);
    }

    /**
     * Pops string from stack of redo
     * @return String from top of stack
     */
    private String popRedo() {
        if (stackRedo.isEmpty())
            return txt_display.getText();
        
        //remove top of stack
        String tmp = stackRedo.remove(stackRedo.size() - 1);
        tmpDisplay = tmp;
        
        //get text of display
        String tmpDisplay = txt_display.getText();
        if (tmpDisplay != popUndoNotRemove())   //display's text isn't same as top of stack
            pushUndo(tmpDisplay);
        isAnswer = popRedoAnswer();
        return tmp;
    }
    
    /**
     * Pops string from stack of redo without remove
     * @return String from top of stack
     */
    private String popRedoNotRemove() {
        if (stackRedo.isEmpty())
            return txt_display.getText();
        return stackRedo.get(stackRedo.size() - 1);
    }
    

    /**
     * Status of display for removing answer for undo action
     */
    private final List<Boolean> stackUndoAnswer = new ArrayList<>();  
    /**
    * Push boolean to stack of answer of undo
    * @param Boolean Boolean to be pushed into stack
    */
    private void pushUndoAnswer(Boolean answer) {
	stackUndoAnswer.add(answer);
    }

    /**
     * Pops answer from stack of answer of undo
     * @return Answer from top of stack
     */
    private Boolean popUndoAnswer() {
        if (stackUndoAnswer.isEmpty())
            return false;
        
        return stackUndoAnswer.remove(stackUndoAnswer.size() - 1);
    }
    
    /**
     * Status of display for removing answer for redo action
     */
    private final List<Boolean> stackRedoAnswer = new ArrayList<>();  
    /**
    * Push boolean to stack of answer of redo
    * @param Boolean Boolean to be pushed into stack
    */
    private void pushRedoAnswer(Boolean answer) {
	stackRedoAnswer.add(answer);
    }

    /**
     * Pops answer from stack of answer of redo
     * @return Answer from top of stack
     */
    private Boolean popRedoAnswer() {
        if (stackRedoAnswer.isEmpty())
            return false;
        
        return stackRedoAnswer.remove(stackRedoAnswer.size() - 1);
    }
    
    /**
     * Creates new form Calc
     */
    public Calc() {
        initComponents();
        txt_display.requestFocus();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_keyboard = new javax.swing.JPanel();
        btn_0 = new javax.swing.JButton();
        btn_1 = new javax.swing.JButton();
        btn_2 = new javax.swing.JButton();
        btn_3 = new javax.swing.JButton();
        btn_4 = new javax.swing.JButton();
        btn_5 = new javax.swing.JButton();
        btn_6 = new javax.swing.JButton();
        btn_7 = new javax.swing.JButton();
        btn_8 = new javax.swing.JButton();
        btn_9 = new javax.swing.JButton();
        btn_point = new javax.swing.JButton();
        btn_mul = new javax.swing.JButton();
        btn_div = new javax.swing.JButton();
        btn_sub = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        btn_neg = new javax.swing.JButton();
        btn_equal = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_back = new javax.swing.JButton();
        btn_exp = new javax.swing.JButton();
        btn_sqrt = new javax.swing.JButton();
        btn_fac = new javax.swing.JButton();
        btn_pi = new javax.swing.JButton();
        btn_abs = new javax.swing.JButton();
        btn_rightBracket = new javax.swing.JButton();
        btn_leftBracket = new javax.swing.JButton();
        btn_hex = new javax.swing.JButton();
        btn_dec = new javax.swing.JButton();
        btn_bin = new javax.swing.JButton();
        txt_display = new javax.swing.JTextField();
        bar_mainMenu = new javax.swing.JMenuBar();
        btn_edit = new javax.swing.JMenu();
        mi_undo = new javax.swing.JMenuItem();
        mi_redo = new javax.swing.JMenuItem();
        mi_copy = new javax.swing.JMenuItem();
        mi_paste = new javax.swing.JMenuItem();
        btn_help = new javax.swing.JMenu();
        mi_about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kalkulačka v0.1");
        setResizable(false);

        btn_0.setText("0");
        btn_0.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_0.setNextFocusableComponent(btn_point);
        btn_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_0ActionPerformed(evt);
            }
        });

        btn_1.setText("1");
        btn_1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_1.setNextFocusableComponent(btn_2);
        btn_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_1ActionPerformed(evt);
            }
        });

        btn_2.setText("2");
        btn_2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_2.setNextFocusableComponent(btn_3);
        btn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_2ActionPerformed(evt);
            }
        });

        btn_3.setText("3");
        btn_3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_3.setNextFocusableComponent(btn_0);
        btn_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_3ActionPerformed(evt);
            }
        });

        btn_4.setText("4");
        btn_4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_4.setNextFocusableComponent(btn_5);
        btn_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_4ActionPerformed(evt);
            }
        });

        btn_5.setText("5");
        btn_5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_5.setNextFocusableComponent(btn_6);
        btn_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_5ActionPerformed(evt);
            }
        });

        btn_6.setText("6");
        btn_6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_6.setNextFocusableComponent(btn_1);
        btn_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_6ActionPerformed(evt);
            }
        });

        btn_7.setText("7");
        btn_7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_7.setNextFocusableComponent(btn_8);
        btn_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_7ActionPerformed(evt);
            }
        });

        btn_8.setText("8");
        btn_8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_8.setNextFocusableComponent(btn_9);
        btn_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_8ActionPerformed(evt);
            }
        });

        btn_9.setText("9");
        btn_9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_9.setNextFocusableComponent(btn_4);
        btn_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_9ActionPerformed(evt);
            }
        });

        btn_point.setText(".");
        btn_point.setToolTipText("Decimal point");
        btn_point.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_point.setNextFocusableComponent(btn_neg);
        btn_point.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pointActionPerformed(evt);
            }
        });

        btn_mul.setText("×");
        btn_mul.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_mul.setNextFocusableComponent(btn_div);
        btn_mul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mulActionPerformed(evt);
            }
        });

        btn_div.setText("÷");
        btn_div.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_div.setNextFocusableComponent(btn_add);
        btn_div.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_divActionPerformed(evt);
            }
        });

        btn_sub.setText("−");
        btn_sub.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_sub.setNextFocusableComponent(btn_equal);
        btn_sub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_subActionPerformed(evt);
            }
        });

        btn_add.setText("+");
        btn_add.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_add.setNextFocusableComponent(btn_sub);
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_neg.setText("+/-");
        btn_neg.setToolTipText("Negation");
        btn_neg.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_neg.setNextFocusableComponent(btn_back);
        btn_neg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_negActionPerformed(evt);
            }
        });

        btn_equal.setText("=");
        btn_equal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_equal.setNextFocusableComponent(btn_exp);
        btn_equal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_equalActionPerformed(evt);
            }
        });

        btn_clear.setText("C");
        btn_clear.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_clear.setNextFocusableComponent(btn_mul);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_back.setText("←");
        btn_back.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_back.setNextFocusableComponent(btn_clear);
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        btn_exp.setText("xʸ");
        btn_exp.setToolTipText("Exponentiation");
        btn_exp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_exp.setNextFocusableComponent(btn_sqrt);
        btn_exp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_expActionPerformed(evt);
            }
        });

        btn_sqrt.setText("√x");
        btn_sqrt.setToolTipText("Square root");
        btn_sqrt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_sqrt.setNextFocusableComponent(btn_fac);
        btn_sqrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sqrtActionPerformed(evt);
            }
        });

        btn_fac.setText("n!");
        btn_fac.setToolTipText("Factorial");
        btn_fac.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_fac.setNextFocusableComponent(btn_abs);
        btn_fac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_facActionPerformed(evt);
            }
        });

        btn_pi.setText("π");
        btn_pi.setToolTipText("Pi value");
        btn_pi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_pi.setNextFocusableComponent(btn_7);
        btn_pi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_piActionPerformed(evt);
            }
        });

        btn_abs.setText("|x|");
        btn_abs.setToolTipText("Absolute value");
        btn_abs.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_abs.setNextFocusableComponent(btn_pi);
        btn_abs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_absActionPerformed(evt);
            }
        });

        btn_rightBracket.setText(")");
        btn_rightBracket.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_rightBracket.setNextFocusableComponent(btn_7);
        btn_rightBracket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rightBracketActionPerformed(evt);
            }
        });

        btn_leftBracket.setText("(");
        btn_leftBracket.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_leftBracket.setNextFocusableComponent(btn_pi);
        btn_leftBracket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_leftBracketActionPerformed(evt);
            }
        });

        btn_hex.setText("hex");
        btn_hex.setToolTipText("Hexadecimal mode");
        btn_hex.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_hex.setNextFocusableComponent(btn_abs);
        btn_hex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hexActionPerformed(evt);
            }
        });

        btn_dec.setText("dec");
        btn_dec.setToolTipText("Decimal mode");
        btn_dec.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_dec.setNextFocusableComponent(btn_fac);
        btn_dec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_decActionPerformed(evt);
            }
        });

        btn_bin.setText("bin");
        btn_bin.setToolTipText("Binary mode");
        btn_bin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_bin.setNextFocusableComponent(btn_sqrt);
        btn_bin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_binActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_keyboardLayout = new javax.swing.GroupLayout(pnl_keyboard);
        pnl_keyboard.setLayout(pnl_keyboardLayout);
        pnl_keyboardLayout.setHorizontalGroup(
            pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_keyboardLayout.createSequentialGroup()
                        .addComponent(btn_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_sqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_abs, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_pi, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_keyboardLayout.createSequentialGroup()
                        .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                                .addComponent(btn_6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_mul, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_div, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                                .addComponent(btn_9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_keyboardLayout.createSequentialGroup()
                        .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                                .addComponent(btn_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                                .addComponent(btn_0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_point, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_neg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_sub, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_equal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnl_keyboardLayout.createSequentialGroup()
                        .addComponent(btn_bin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_dec, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_hex, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_leftBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_rightBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        pnl_keyboardLayout.setVerticalGroup(
            pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_dec, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hex, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_leftBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_rightBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_abs, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_div, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_mul, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sub, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_point, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_neg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_equal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txt_display.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        txt_display.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_display.setFocusCycleRoot(true);
        txt_display.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_displayKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_displayKeyReleased(evt);
            }
        });

        btn_edit.setText("Edit");

        mi_undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        mi_undo.setText("Undo");
        mi_undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_undoActionPerformed(evt);
            }
        });
        btn_edit.add(mi_undo);

        mi_redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mi_redo.setText("Redo");
        mi_redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_redoActionPerformed(evt);
            }
        });
        btn_edit.add(mi_redo);

        mi_copy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mi_copy.setText("Copy");
        mi_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_copyActionPerformed(evt);
            }
        });
        btn_edit.add(mi_copy);

        mi_paste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mi_paste.setText("Paste");
        mi_paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_pasteActionPerformed(evt);
            }
        });
        btn_edit.add(mi_paste);

        bar_mainMenu.add(btn_edit);

        btn_help.setText("Help");

        mi_about.setText("About");
        mi_about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_aboutActionPerformed(evt);
            }
        });
        btn_help.add(mi_about);

        bar_mainMenu.add(btn_help);

        setJMenuBar(bar_mainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_keyboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_display)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_display, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_keyboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_1ActionPerformed
        displayAdd('1');
    }//GEN-LAST:event_btn_1ActionPerformed

    private void btn_0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_0ActionPerformed
        displayAdd('0');
    }//GEN-LAST:event_btn_0ActionPerformed

    private void btn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_2ActionPerformed
        displayAdd('2');
    }//GEN-LAST:event_btn_2ActionPerformed

    private void btn_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_3ActionPerformed
        displayAdd('3');
    }//GEN-LAST:event_btn_3ActionPerformed

    private void btn_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_4ActionPerformed
        displayAdd('4');
    }//GEN-LAST:event_btn_4ActionPerformed

    private void btn_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_5ActionPerformed
        displayAdd('5');
    }//GEN-LAST:event_btn_5ActionPerformed

    private void btn_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_6ActionPerformed
        displayAdd('6');
    }//GEN-LAST:event_btn_6ActionPerformed

    private void btn_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_7ActionPerformed
        displayAdd('7');
    }//GEN-LAST:event_btn_7ActionPerformed

    private void btn_8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_8ActionPerformed
        displayAdd('8');
    }//GEN-LAST:event_btn_8ActionPerformed

    private void btn_9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_9ActionPerformed
        displayAdd('9');        
    }//GEN-LAST:event_btn_9ActionPerformed

    private void btn_pointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pointActionPerformed
        displayAdd('.');
    }//GEN-LAST:event_btn_pointActionPerformed

    private void btn_negActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_negActionPerformed
        calculate();        
        
        math.mul(numberNeg);
        setDisplay();
    }//GEN-LAST:event_btn_negActionPerformed

    private void btn_mulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mulActionPerformed
        displayAdd('*');
    }//GEN-LAST:event_btn_mulActionPerformed

    private void btn_divActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_divActionPerformed
        displayAdd('/');
    }//GEN-LAST:event_btn_divActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        displayAdd('+');
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_subActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_subActionPerformed
        displayAdd('−');
    }//GEN-LAST:event_btn_subActionPerformed

    private void btn_equalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_equalActionPerformed
        calculate();
    }//GEN-LAST:event_btn_equalActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        displayClear(false);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        displayClear(true);
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_expActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_expActionPerformed
        displayAdd('^');
    }//GEN-LAST:event_btn_expActionPerformed

    private void btn_sqrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sqrtActionPerformed
        calculate();
        math.sqrt();
        setDisplay();
    }//GEN-LAST:event_btn_sqrtActionPerformed

    private void btn_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_facActionPerformed
        calculate();
        math.fac();
        setDisplay();
    }//GEN-LAST:event_btn_facActionPerformed

    private void btn_absActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_absActionPerformed
        calculate();
        math.abs();
        setDisplay();
    }//GEN-LAST:event_btn_absActionPerformed

    private void btn_piActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_piActionPerformed
        math.setPI();
        String tmp = txt_display.getText() + math.getValue().toString();
        txt_display.setText(tmp);
        txt_display.requestFocus();
    }//GEN-LAST:event_btn_piActionPerformed

    private void txt_displayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_displayKeyPressed
        checkEmptyDisplay();
        
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL)
            isCtrlActive = true;      
        else if (!isCtrlActive) {
            pushUndo(txt_display.getText()); 
            stackRedo.clear();
        
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                calculate();
            }  
            else if(evt.getKeyCode() != KeyEvent.VK_SHIFT && evt.getKeyCode() != KeyEvent.VK_ALT_GRAPH)
                checkAnswer(evt.getKeyChar());
        }
        else {
            if(evt.getKeyCode() == KeyEvent.VK_V) {                
                checkAnswer('a');
            }
        }
               
    }//GEN-LAST:event_txt_displayKeyPressed

    private void btn_rightBracketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rightBracketActionPerformed
        displayAdd(')');
    }//GEN-LAST:event_btn_rightBracketActionPerformed

    private void btn_leftBracketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_leftBracketActionPerformed
        displayAdd('(');
    }//GEN-LAST:event_btn_leftBracketActionPerformed

    private void btn_hexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hexActionPerformed
        base = Base.hex;
        txt_display.requestFocus();
    }//GEN-LAST:event_btn_hexActionPerformed

    private void btn_decActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_decActionPerformed
        base = Base.dec;
        txt_display.requestFocus();
    }//GEN-LAST:event_btn_decActionPerformed

    private void btn_binActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_binActionPerformed
        base = Base.bin;
        txt_display.requestFocus();
    }//GEN-LAST:event_btn_binActionPerformed

    private void mi_undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_undoActionPerformed
        txt_display.setText(popUndo());
    }//GEN-LAST:event_mi_undoActionPerformed

    private void mi_redoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_redoActionPerformed
        txt_display.setText(popRedo());
    }//GEN-LAST:event_mi_redoActionPerformed

    private void txt_displayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_displayKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL)
            isCtrlActive = false;
    }//GEN-LAST:event_txt_displayKeyReleased

    private void mi_pasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_pasteActionPerformed
        checkAnswer('a');
        txt_display.paste();
    }//GEN-LAST:event_mi_pasteActionPerformed

    private void mi_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_copyActionPerformed
        txt_display.copy();
    }//GEN-LAST:event_mi_copyActionPerformed

    private void mi_aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_aboutActionPerformed
        JOptionPane.showMessageDialog(this, "This is calculator created for IVS.\n\nAuthor(s): \nZnebejánek Tomáš\nŠvasta Michael\nBuchta Martin\nSichkaruk Roman \n\n\n© 2016", "About Calc v0.1", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_mi_aboutActionPerformed

    private void displayAdd(char value){   
        //System.out.println("print: " );
        checkAnswer(value);
        String displayText = txt_display.getText();
        pushUndo(displayText);
        stackRedo.clear();
        displayText += Character.toString(value);
        txt_display.setText(displayText);
        txt_display.requestFocus();
    }
    
    private void displayClear(boolean all){
        String tmpText = txt_display.getText();
        pushUndo(tmpText);
        stackRedo.clear();
        if (all) {
            txt_display.setText("");
        }
        else if (!tmpText.isEmpty()) {            
            String displayText = tmpText.substring(0, tmpText.length()-1);
            txt_display.setText(displayText);
        }
        checkEmptyDisplay();
        txt_display.requestFocus();
    }
    
    private void checkEmptyDisplay(){
        if (txt_display.getText().isEmpty()) {
            math.reset();
        }
    }
            
    
    private void calculate(){
        txt_display.requestFocus();
        if (txt_display.getText().length() == 0)
        { return; }
        
        try {            
                math.calculateFormula(txt_display.getText(), base.getValue());  
                setDisplay();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Bad format of input data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
       
    private void setDisplay(){
        txt_display.setText(math.getValue().toString());
        isAnswer = true;
    }
    
    private void checkAnswer(char key){
        switch (key) {
            case '+':
            case '-':
            case '!':
            case '*':
            case '/':
            case '^':
                isAnswer = false;
	}
        
        if (isAnswer){
            displayClear(true);
            isAnswer = false;
        }
    } 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
 
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calc().setVisible(true);                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar bar_mainMenu;
    private javax.swing.JButton btn_0;
    private javax.swing.JButton btn_1;
    private javax.swing.JButton btn_2;
    private javax.swing.JButton btn_3;
    private javax.swing.JButton btn_4;
    private javax.swing.JButton btn_5;
    private javax.swing.JButton btn_6;
    private javax.swing.JButton btn_7;
    private javax.swing.JButton btn_8;
    private javax.swing.JButton btn_9;
    private javax.swing.JButton btn_abs;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_bin;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_dec;
    private javax.swing.JButton btn_div;
    private javax.swing.JMenu btn_edit;
    private javax.swing.JButton btn_equal;
    private javax.swing.JButton btn_exp;
    private javax.swing.JButton btn_fac;
    private javax.swing.JMenu btn_help;
    private javax.swing.JButton btn_hex;
    private javax.swing.JButton btn_leftBracket;
    private javax.swing.JButton btn_mul;
    private javax.swing.JButton btn_neg;
    private javax.swing.JButton btn_pi;
    private javax.swing.JButton btn_point;
    private javax.swing.JButton btn_rightBracket;
    private javax.swing.JButton btn_sqrt;
    private javax.swing.JButton btn_sub;
    private javax.swing.JMenuItem mi_about;
    private javax.swing.JMenuItem mi_copy;
    private javax.swing.JMenuItem mi_paste;
    private javax.swing.JMenuItem mi_redo;
    private javax.swing.JMenuItem mi_undo;
    private javax.swing.JPanel pnl_keyboard;
    private javax.swing.JTextField txt_display;
    // End of variables declaration//GEN-END:variables
}
