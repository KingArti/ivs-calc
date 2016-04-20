package CalcLayout;

import java.awt.event.KeyEvent;
import IVS.IVSMath;
import IVS.IVSMathImpl;
import IVS.IVSNumber;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;



/**
 * Description: Main class for Calc UI
 * @author Michael Švasta, xsvast00@stud.fit.vutbr.cz
 */
public class Calc extends javax.swing.JFrame {
    /**
     * New instance of interface for math library
     */
    IVSMath math = new IVSMathImpl();  
    /**
     * Internal variable (-1) for negation of value on display
     */
    private static final IVSNumber numberNeg = new IVSNumber(-1);  
    /**
     * Status of display, true means answer on display
     */
    boolean isAnswer = false;       
    /**
     * Last status of display
     */
    boolean wasAnswer = false;      
    
    /**
     * Base of operations
     */
    private enum Base { 
        bin(2), oct(8), dec(10), hex(16);
        
        private final int value;
        private Base(int value){
            this.value = value;
        }
        
        public int getValue(){
            return value;
        }
    }
    
    /**
     * New instance of base
     */
    Base base = Base.dec;   
    
    /**
     * List of buttons, which are enabled only in hexadecimal mode
     */
    List<JButton> listOfNonDecimal = new ArrayList<>();
    /**
     * Inicialization of list of hexadecimal buttons
     */
    private void initListOfNonDecimal(){
        listOfNonDecimal.add(btn_a);
        listOfNonDecimal.add(btn_b);
        listOfNonDecimal.add(btn_c);
        listOfNonDecimal.add(btn_d);
        listOfNonDecimal.add(btn_e);
        listOfNonDecimal.add(btn_f);
    }
    
    /**
     * List of buttons, which are enabled only in decimal and hexadecimal mode
     */
    List<JButton> listOfNonOctal = new ArrayList<>();
    /**
     * Inicialization of list of decimal and hexadecimal buttons
     */
    private void initListOfNonOctal(){
        listOfNonOctal.add(btn_8);
        listOfNonOctal.add(btn_9);
    }
    
    /**
     * List of buttons, which are enabled only in octal, decimal and hexadecimal mode
     */
    List<JButton> listOfNonBinary = new ArrayList<>();
    /**
     * Inicialization of list of octal, decimal and hexadecimal buttons
     */
    private void initListOfNonBinary(){
        listOfNonBinary.add(btn_2);
        listOfNonBinary.add(btn_3);
        listOfNonBinary.add(btn_4);
        listOfNonBinary.add(btn_5);
        listOfNonBinary.add(btn_6);
        listOfNonBinary.add(btn_7);
    }
    
    /**
     * List of buttons, which are enabled only in decimal mode
     */
    List<JButton> listOfOnlyDecimal = new ArrayList<>();
    /**
     * Inicialization of list of decimal buttons
     */
    private void initListOfOnlyDecimal(){
        listOfOnlyDecimal.add(btn_fac);
        listOfOnlyDecimal.add(btn_abs);
        listOfOnlyDecimal.add(btn_exp);
        listOfOnlyDecimal.add(btn_sqrt);
        listOfOnlyDecimal.add(btn_pi);
        listOfOnlyDecimal.add(btn_neg);
        listOfOnlyDecimal.add(btn_point);
    }
    
    /**
     * Status of control key
     */
    boolean isCtrlActive = false;   
    
    /**
     * Stack of undo actions
     */
    private final List<String> stackUndo = new ArrayList<>();  

    /**
     * Push string to stack of undo
     * @param displayText String to be pushed into stack
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
        
        //get text of display
        String tmpDisplay = txt_display.getText();
        if (tmpDisplay !=popRedoNotRemove())   //display's text isn't same as top of stack
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
    * @param displayText String to be pushed into stack
    */
    private void pushRedo(String displayText) {
	stackRedo.add(displayText);
        pushRedoAnswer(isAnswer);    
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
     * Clear stack of redo
     */
    private void clearRedo() {
        stackRedo.clear();
    }

    /**
     * Status of display for removing answer for undo action
     */
    private final List<Boolean> stackUndoAnswer = new ArrayList<>();  
    /**
    * Push boolean to stack of answer of undo
    * @param answer Boolean to be pushed into stack
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
    * @param answer Boolean to be pushed into stack
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
        initListOfNonBinary();
        initListOfNonOctal();
        initListOfNonDecimal();    
        initListOfOnlyDecimal();
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
        cmb_mode = new javax.swing.JComboBox<>();
        btn_a = new javax.swing.JButton();
        btn_b = new javax.swing.JButton();
        btn_c = new javax.swing.JButton();
        btn_d = new javax.swing.JButton();
        btn_e = new javax.swing.JButton();
        btn_f = new javax.swing.JButton();
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
        setTitle("Calc v0.1");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        btn_0.setText("0");
        btn_0.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_0ActionPerformed(evt);
            }
        });

        btn_1.setText("1");
        btn_1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_1ActionPerformed(evt);
            }
        });

        btn_2.setText("2");
        btn_2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_2ActionPerformed(evt);
            }
        });

        btn_3.setText("3");
        btn_3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_3ActionPerformed(evt);
            }
        });

        btn_4.setText("4");
        btn_4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_4ActionPerformed(evt);
            }
        });

        btn_5.setText("5");
        btn_5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_5ActionPerformed(evt);
            }
        });

        btn_6.setText("6");
        btn_6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_6ActionPerformed(evt);
            }
        });

        btn_7.setText("7");
        btn_7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_7ActionPerformed(evt);
            }
        });

        btn_8.setText("8");
        btn_8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_8ActionPerformed(evt);
            }
        });

        btn_9.setText("9");
        btn_9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_9ActionPerformed(evt);
            }
        });

        btn_point.setText(".");
        btn_point.setToolTipText("Decimal point");
        btn_point.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_point.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pointActionPerformed(evt);
            }
        });

        btn_mul.setText("×");
        btn_mul.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_mul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mulActionPerformed(evt);
            }
        });

        btn_div.setText("÷");
        btn_div.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_div.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_divActionPerformed(evt);
            }
        });

        btn_sub.setText("−");
        btn_sub.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_sub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_subActionPerformed(evt);
            }
        });

        btn_add.setText("+");
        btn_add.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_neg.setText("+/-");
        btn_neg.setToolTipText("Negation");
        btn_neg.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_neg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_negActionPerformed(evt);
            }
        });

        btn_equal.setText("=");
        btn_equal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_equal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_equalActionPerformed(evt);
            }
        });

        btn_clear.setText("CLR");
        btn_clear.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_back.setText("←");
        btn_back.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        btn_exp.setText("xʸ");
        btn_exp.setToolTipText("Exponentiation");
        btn_exp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_exp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_expActionPerformed(evt);
            }
        });

        btn_sqrt.setText("√x");
        btn_sqrt.setToolTipText("Square root");
        btn_sqrt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_sqrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sqrtActionPerformed(evt);
            }
        });

        btn_fac.setText("n!");
        btn_fac.setToolTipText("Factorial");
        btn_fac.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_fac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_facActionPerformed(evt);
            }
        });

        btn_pi.setText("π");
        btn_pi.setToolTipText("Pi value");
        btn_pi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_pi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_piActionPerformed(evt);
            }
        });

        btn_abs.setText("|x|");
        btn_abs.setToolTipText("Absolute value");
        btn_abs.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_abs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_absActionPerformed(evt);
            }
        });

        btn_rightBracket.setText(")");
        btn_rightBracket.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_rightBracket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rightBracketActionPerformed(evt);
            }
        });

        btn_leftBracket.setText("(");
        btn_leftBracket.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_leftBracket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_leftBracketActionPerformed(evt);
            }
        });

        cmb_mode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Binary", "Octal", "Decimal", "Hexadecimal" }));
        cmb_mode.setSelectedIndex(3);
        cmb_mode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_modeActionPerformed(evt);
            }
        });

        btn_a.setText("A");
        btn_a.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aActionPerformed(evt);
            }
        });

        btn_b.setText("B");
        btn_b.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bActionPerformed(evt);
            }
        });

        btn_c.setText("C");
        btn_c.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cActionPerformed(evt);
            }
        });

        btn_d.setText("D");
        btn_d.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_d.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dActionPerformed(evt);
            }
        });

        btn_e.setText("E");
        btn_e.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_e.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eActionPerformed(evt);
            }
        });

        btn_f.setText("F");
        btn_f.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btn_f.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_keyboardLayout = new javax.swing.GroupLayout(pnl_keyboard);
        pnl_keyboard.setLayout(pnl_keyboardLayout);
        pnl_keyboardLayout.setHorizontalGroup(
            pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                .addComponent(btn_4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_mul, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_div, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(cmb_mode, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_leftBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_rightBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                .addComponent(btn_7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                .addComponent(btn_a, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_b, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_c, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_d, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_e, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_f, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_keyboardLayout.setVerticalGroup(
            pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_keyboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_leftBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_rightBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_mode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_abs, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_pi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_d, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_e, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_f, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_a, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_b, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_c, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_keyboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(btn_equal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(pnl_keyboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_display))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_display, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_keyboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Click action of button "1" 
     * @param evt Action event
     */
    private void btn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_1ActionPerformed
        displayAdd('1');
    }//GEN-LAST:event_btn_1ActionPerformed

    /**
     * Click action of button "0" 
     * @param evt Action event
     */
    private void btn_0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_0ActionPerformed
        displayAdd('0');
    }//GEN-LAST:event_btn_0ActionPerformed

    /**
     * Click action of button "2" 
     * @param evt Action event
     */
    private void btn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_2ActionPerformed
        displayAdd('2');
    }//GEN-LAST:event_btn_2ActionPerformed

    /**
     * Click action of button "3" 
     * @param evt Action event
     */
    private void btn_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_3ActionPerformed
        displayAdd('3');
    }//GEN-LAST:event_btn_3ActionPerformed

    /**
     * Click action of button "4" 
     * @param evt Action event
     */
    private void btn_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_4ActionPerformed
        displayAdd('4');
    }//GEN-LAST:event_btn_4ActionPerformed

    /**
     * Click action of button "5" 
     * @param evt Action event
     */
    private void btn_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_5ActionPerformed
        displayAdd('5');
    }//GEN-LAST:event_btn_5ActionPerformed

    /**
     * Click action of button "6" 
     * @param evt Action event
     */
    private void btn_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_6ActionPerformed
        displayAdd('6');
    }//GEN-LAST:event_btn_6ActionPerformed

    /**
     * Click action of button "7" 
     * @param evt Action event
     */
    private void btn_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_7ActionPerformed
        displayAdd('7');
    }//GEN-LAST:event_btn_7ActionPerformed

    /**
     * Click action of button "8" 
     * @param evt Action event
     */
    private void btn_8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_8ActionPerformed
        displayAdd('8');
    }//GEN-LAST:event_btn_8ActionPerformed

    /**
     * Click action of button "9" 
     * @param evt Action event
     */
    private void btn_9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_9ActionPerformed
        displayAdd('9');        
    }//GEN-LAST:event_btn_9ActionPerformed

    /**
     * Click action of button "." 
     * @param evt Action event
     */
    private void btn_pointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pointActionPerformed
        displayAdd('.');
    }//GEN-LAST:event_btn_pointActionPerformed

    /**
     * Click action of button "+/-" 
     * @param evt Action event
     */
    private void btn_negActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_negActionPerformed
        calculate();        
        
        math.mul(numberNeg);
        setDisplay();
    }//GEN-LAST:event_btn_negActionPerformed

    /**
     * Click action of button "×" 
     * @param evt Action event
     */
    private void btn_mulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mulActionPerformed
        displayAdd('*');
    }//GEN-LAST:event_btn_mulActionPerformed

    /**
     * Click action of button "÷" 
     * @param evt Action event
     */
    private void btn_divActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_divActionPerformed
        displayAdd('/');
    }//GEN-LAST:event_btn_divActionPerformed

    /**
     * Click action of button "+" 
     * @param evt Action event
     */
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        displayAdd('+');
    }//GEN-LAST:event_btn_addActionPerformed

    /**
     * Click action of button "−" 
     * @param evt Action event
     */
    private void btn_subActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_subActionPerformed
        displayAdd('-');
    }//GEN-LAST:event_btn_subActionPerformed

    /**
     * Click action of button "=" 
     * @param evt Action event
     */
    private void btn_equalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_equalActionPerformed

        calculate();
    }//GEN-LAST:event_btn_equalActionPerformed

    /**
     * Click action of button "←" 
     * @param evt Action event
     */
    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        displayClear(false);
    }//GEN-LAST:event_btn_backActionPerformed

    /**
     * Click action of button "C" 
     * @param evt Action event
     */
    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        displayClear(true);
    }//GEN-LAST:event_btn_clearActionPerformed

    /**
     * Click action of button "xʸ" 
     * @param evt Action event
     */
    private void btn_expActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_expActionPerformed
        displayAdd('^');
    }//GEN-LAST:event_btn_expActionPerformed

    /**
     * Click action of button "√x" 
     * @param evt Action event
     */
    private void btn_sqrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sqrtActionPerformed
        calculate();
        math.sqrt();
        setDisplay();
    }//GEN-LAST:event_btn_sqrtActionPerformed

    /**
     * Click action of button "n!" 
     * @param evt Action event
     */
    private void btn_facActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_facActionPerformed
        calculate();
        math.fac();
        setDisplay();
    }//GEN-LAST:event_btn_facActionPerformed

    /**
     * Click action of button "|x|" 
     * @param evt Action event
     */
    private void btn_absActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_absActionPerformed
        calculate();
        math.abs();
        setDisplay();
    }//GEN-LAST:event_btn_absActionPerformed

    /**
     * Click action of button "π" 
     * @param evt Action event
     */
    private void btn_piActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_piActionPerformed
        math.setPI();
        String tmp = txt_display.getText() + math.getValue().toString();
        txt_display.setText(tmp);
        txt_display.requestFocus();
    }//GEN-LAST:event_btn_piActionPerformed

    /**
     * Display's key pressed action
     * @param evt Key event
     */
    private void txt_displayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_displayKeyPressed
        checkEmptyDisplay();    //check, if display is empty for reset math library
        
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL)    //if ctlr is pressed
            isCtrlActive = true;                        //boolean is activated
        else if (!isCtrlActive 
                && evt.getKeyCode() != KeyEvent.VK_HOME
                && evt.getKeyCode() != KeyEvent.VK_END
                && evt.getKeyCode() != KeyEvent.VK_LEFT
                && evt.getKeyCode() != KeyEvent.VK_RIGHT) { //else if ctrl is not active, and cursor is not moving
            
        
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    //if enter is pressed
                calculate();                                //calculate
            }  
            else if(evt.getKeyCode() != KeyEvent.VK_SHIFT && evt.getKeyCode() != KeyEvent.VK_ALT_GRAPH) {   //if not pressed shift or alt gr
                pushUndo(txt_display.getText());                //push stack of undo actions
                clearRedo();                                    //clear stack of redo actions
                checkAnswer(evt.getKeyChar());      //check, if answer is on display (for actual pressed key)
            }
        }
        else if(evt.getKeyCode() == KeyEvent.VK_V) {   //ctrl+v is pressed (isCtrlActive == true)
            checkAnswer('a');   //check, if answer is on display (for random char, it doesn't matter)
        }                       
    }//GEN-LAST:event_txt_displayKeyPressed

    /**
     * Click action of button ")" 
     * @param evt Action event
     */
    private void btn_rightBracketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rightBracketActionPerformed
        displayAdd(')');
    }//GEN-LAST:event_btn_rightBracketActionPerformed

    /**
     * Click action of button "(" 
     * @param evt Action event
     */
    private void btn_leftBracketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_leftBracketActionPerformed
        displayAdd('(');
    }//GEN-LAST:event_btn_leftBracketActionPerformed

    /**
     * Click action of "Undo" button in main menu
     * @param evt Action event
     */
    private void mi_undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_undoActionPerformed
        txt_display.setText(popUndo());
    }//GEN-LAST:event_mi_undoActionPerformed

    /**
     * Click action of "Redo" button in main menu
     * @param evt Action event
     */
    private void mi_redoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_redoActionPerformed
        txt_display.setText(popRedo());
    }//GEN-LAST:event_mi_redoActionPerformed

    /**
     * Display's key released action
     * @param evt Key event
     */
    private void txt_displayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_displayKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL)    //ctrl is released
            isCtrlActive = false;
    }//GEN-LAST:event_txt_displayKeyReleased

    /**
     * Click action of "Paste" button in main menu
     * @param evt Action event
     */
    private void mi_pasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_pasteActionPerformed
        checkAnswer('a');
        txt_display.paste();
    }//GEN-LAST:event_mi_pasteActionPerformed

    /**
     * Click action of "Copy" button in main menu
     * @param evt Action event
     */
    private void mi_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_copyActionPerformed
        txt_display.copy();
    }//GEN-LAST:event_mi_copyActionPerformed

    /**
     * Click action of "About" button in main menu
     * @param evt Action event
     */
    private void mi_aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_aboutActionPerformed
        JOptionPane.showMessageDialog(this, "This is calculator created for IVS.\n\nAuthor(s): \nZnebejánek Tomáš\nŠvasta Michael\nBuchta Martin\nSichkaruk Roman \n\n\n© 2016", "About Calc v0.1", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_mi_aboutActionPerformed

    /**
     * Selection of new item in combobox
     * @param evt Combobox action event
     */
    private void cmb_modeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_modeActionPerformed
        calculate();        
        
        if(cmb_mode.getSelectedIndex() == 0) {          //binary
            base = Base.bin;
            setEnabledButtons(listOfNonBinary, false);
            setEnabledButtons(listOfNonOctal, false);
            setEnabledButtons(listOfOnlyDecimal, false);
            setVisibleHexButtons(false);
            //btn_2.setEnabled(false);
        }
        else if(cmb_mode.getSelectedIndex() == 1) {     //octal
            base = Base.oct;
            setEnabledButtons(listOfNonBinary, true);
            setEnabledButtons(listOfNonOctal, false);
            setEnabledButtons(listOfOnlyDecimal, false);            
            setVisibleHexButtons(false);
        }
        else if(cmb_mode.getSelectedIndex() == 2) {     //decimal
            base = Base.dec;
            setEnabledButtons(listOfNonBinary, true);
            setEnabledButtons(listOfNonOctal, true);
            setEnabledButtons(listOfOnlyDecimal, true);
            setVisibleHexButtons(false);
        }
        else if(cmb_mode.getSelectedIndex() == 3) {     //hexadecimal
            base = Base.hex;
            setEnabledButtons(listOfNonBinary, true);
            setEnabledButtons(listOfNonOctal, true);
            setEnabledButtons(listOfOnlyDecimal, false);
            setVisibleHexButtons(true);
        }
        
        txt_display.requestFocus();
    }//GEN-LAST:event_cmb_modeActionPerformed

    /**
     * Click action of button "e" 
     * @param evt Action event
     */
    private void btn_eActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eActionPerformed
        displayAdd('e');
    }//GEN-LAST:event_btn_eActionPerformed

    /**
     * Click action of button "d" 
     * @param evt Action event
     */
    private void btn_dActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dActionPerformed
        displayAdd('d');
    }//GEN-LAST:event_btn_dActionPerformed

    /**
     * Click action of button "c" 
     * @param evt Action event
     */
    private void btn_cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cActionPerformed
        displayAdd('c');
    }//GEN-LAST:event_btn_cActionPerformed

    /**
     * Click action of button "b" 
     * @param evt Action event
     */
    private void btn_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bActionPerformed
        displayAdd('b');
    }//GEN-LAST:event_btn_bActionPerformed

    /**
     * Click action of button "a" 
     * @param evt Action event
     */
    private void btn_aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aActionPerformed
        displayAdd('a');
    }//GEN-LAST:event_btn_aActionPerformed

    /**
     * Click action of button "f" 
     * @param evt Action event
     */
    private void btn_fActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fActionPerformed
        displayAdd('f');
    }//GEN-LAST:event_btn_fActionPerformed

    /**
     * Activating window's form
     * @param evt Action event
     */
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        cmb_mode.setSelectedIndex(2);   //select decimal mode
    }//GEN-LAST:event_formWindowActivated

    /**
     * Set state of buttons for mode
     * @param buttonsList List of buttons, which is working with
     * @param state Boolean value, which is setted
     */
    private void setEnabledButtons(List<JButton> buttonsList, boolean state){
        for (JButton b : buttonsList)
                b.setEnabled(state);
    }
    
    /**
     * Setting visibility of hexadecimal buttons
     * @param state Boolean value, which is setted
     */
    private void setVisibleHexButtons(boolean state) {
        if (btn_a.isVisible() && !state) {
            int h = this.getSize().height;
            int w = this.getSize().width;
            this.setSize(w, h-45);
        }
        else if (!btn_a.isVisible() && state) {
            int h = this.getSize().height;
            int w = this.getSize().width;
            this.setSize(w, h+45);
        } 
        
        for (JButton b : listOfNonDecimal)
            b.setVisible(state);        
    }
    
    /**
     * Add char on display
     * @param value Char, which is adding on display
     */
    private void displayAdd(char value){          
        checkAnswer(value);             //check, if is answer on display
        String displayText = txt_display.getText();
        pushUndo(displayText);          //push into undo stack
        clearRedo();                    //clear redo stack
        
        insertCharIntoCursorPosition(value, displayText);   //insert char onto cursor position in textfield
      
        txt_display.requestFocus();   
    }
    
    /**
     * Clear text from display
     * @param all True clear all text on display, False delete char before cursor
     */
    private void displayClear(boolean all){
        String tmpText = txt_display.getText();
        pushUndo(tmpText);  //undo stack
        clearRedo();        //redo stack
        if (all) {
            txt_display.setText("");    //clear all
        }
        else if (!tmpText.isEmpty()) {                  //is not epmty
            insertCharIntoCursorPosition(' ', tmpText); //delete char before cursor
        }
        checkEmptyDisplay();        //reset math library for clear display
        txt_display.requestFocus();
    }
    
    /**
     * Insert char (or delete char) onto cursor position in textfield
     * @param value Char, which is inserting, or ' ' for deleting char
     * @param displayText String of display's text
     */
    private void insertCharIntoCursorPosition(char value, String displayText){              
        int cursorPosition = txt_display.getCaretPosition();    //get cursor position
        
        String tmp;
        String tmp2 = displayText.substring(cursorPosition);    //second part of display's text
        if (value != ' '){                                      //inserting
            tmp = displayText.substring(0, cursorPosition);     //first part of display's text
            tmp += Character.toString(value);                   //insert char on end of first part of text
            cursorPosition++;                                   //increment cursor position
        }
        else{                                                   //deleting
            tmp = displayText.substring(0, cursorPosition-1);   //first part of display's text without deleting char
            cursorPosition--;                                   //decrement cursor position
            isAnswer = false;                                   //answer is false
        }            
        
        tmp += tmp2;                                            //add second part to first
        txt_display.setText(tmp);                               //set display
        txt_display.setCaretPosition(cursorPosition);           //set cursor position
    }
    
    /**
     * Check, if display is empty for reset math library
     */
    private void checkEmptyDisplay(){
        if (txt_display.getText().isEmpty()) {
            math.reset();
        }
    }
            
    /**
     * Calculate from display's text
     */
    private void calculate(){    
        
        if (txt_display.getText().length() == 0)
        { return; }
        
        pushUndo(txt_display.getText());            //push stack of undo actions
        clearRedo();                                //clear stack of redo actions
        
        try {            
            math.calculateFormula(txt_display.getText(), base.getValue());  //calculate from math library
            setDisplay();                                                   //set answer on display
        } catch (Exception ex) {    //catch exception
            JOptionPane.showMessageDialog(this, "Bad format of input data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        txt_display.requestFocus();
    }
       
    /**
     * Get answer of calculate from math library
     */
    private void setDisplay(){        
        txt_display.setText(math.getValue().toString());
        isAnswer = true;
    }
    
    /**
     * Check, if answer is on display
     * @param key Char, which is adding on display
     */
    private void checkAnswer(char key){
        switch (key) {  //for operation answer is false, for numeric value is true
            case '+':
            case '-':
            case '!':
            case '*':
            case '/':
            case '^':
                isAnswer = false;
	}
        
        
        if (isAnswer){          //if answer is true
            displayClear(true); //clear display
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
    private javax.swing.JButton btn_a;
    private javax.swing.JButton btn_abs;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_b;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_c;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_d;
    private javax.swing.JButton btn_div;
    private javax.swing.JButton btn_e;
    private javax.swing.JMenu btn_edit;
    private javax.swing.JButton btn_equal;
    private javax.swing.JButton btn_exp;
    private javax.swing.JButton btn_f;
    private javax.swing.JButton btn_fac;
    private javax.swing.JMenu btn_help;
    private javax.swing.JButton btn_leftBracket;
    private javax.swing.JButton btn_mul;
    private javax.swing.JButton btn_neg;
    private javax.swing.JButton btn_pi;
    private javax.swing.JButton btn_point;
    private javax.swing.JButton btn_rightBracket;
    private javax.swing.JButton btn_sqrt;
    private javax.swing.JButton btn_sub;
    private javax.swing.JComboBox<String> cmb_mode;
    private javax.swing.JMenuItem mi_about;
    private javax.swing.JMenuItem mi_copy;
    private javax.swing.JMenuItem mi_paste;
    private javax.swing.JMenuItem mi_redo;
    private javax.swing.JMenuItem mi_undo;
    private javax.swing.JPanel pnl_keyboard;
    private javax.swing.JTextField txt_display;
    // End of variables declaration//GEN-END:variables
}
