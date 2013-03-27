/* Generated By:JavaCC: Do not edit this line. Yaka.java */
public class Yaka implements Constantes, YakaConstants {

    public static Declaration decl;
    public static TabIdent tabIdent;
    public static Expression expr;
    public static Fonctions fonc;
    public static YVM yvm;
    public static String nomVarTemp="";

  public static void main(String args[]) {
    Yaka analyseur;
    java.io.InputStream input;

    if (args.length==1) {
      System.out.print(args[args.length-1] + ": ");
      try {
        input = new java.io.FileInputStream(args[args.length-1]+".yaka");
      } catch (java.io.FileNotFoundException e) {
        System.out.println("Fichier introuvable.");
        return;
      }
    } else if (args.length==0) {
      System.out.println("Lecture sur l'entree standard...");
      input = System.in;
    } else {
      System.out.println("Usage: java Gram [fichier]");
      return;
    }
    try {
      tabIdent = new TabIdent();
      decl = new Declaration(tabIdent);
      expr = new Expression(tabIdent);
      fonc = new Fonctions(tabIdent,expr);
      analyseur = new Yaka(input);
      yvm = new YVM(args[args.length-1]);
      analyseur.analyse();
      System.out.println("analyse syntaxique reussie!");
    } catch (ParseException e) {
      String msg = e.getMessage();
      msg = msg.substring(0,msg.indexOf("\u005cn"));
      System.out.println("Erreur de syntaxe : "+msg);
    }
  }

/**************************************/
/********debut de la grammaire ********/
/**************************************/
  static final public void analyse() throws ParseException {
    jj_consume_token(PROGRAMME);
                yvm.entete();
    jj_consume_token(ident);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEEN:
      case ENT:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      declFonction();
    }
    jj_consume_token(PRINCIPAL);
    yvm.nomFonc("main");fonc.ajouteFonction("principale");
    bloc();
    jj_consume_token(FPRINCIPAL);
                tabIdent.videLocaux();
    jj_consume_token(FPROGRAMME);
    yvm.queue();
  }

  static final public void bloc() throws ParseException {
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONST:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      declConst();
    }
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VAR:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_3;
      }
      declVar();
    }
               yvm.ouvreBloc(tabIdent.nombreVariable()*2);
    suiteInstr();
  }

  static final public void declFonction() throws ParseException {
    type();
    jj_consume_token(FONCTION);
    jj_consume_token(ident);
                                   fonc.ajouteFonction(YakaTokenManager.identLu);
        yvm.nomFonc(YakaTokenManager.identLu);
    paramForms();
         fonc.calculerOffsetParam();
    bloc();
    jj_consume_token(FFONCTION);
        yvm.fermeBloc(fonc.getTailleParam());
        tabIdent.videLocaux();
        fonc.depilerFonction();
        expr.clear();
  }

  static final public void paramForms() throws ParseException {
    jj_consume_token(40);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BOOLEEN:
    case ENT:
      paramForm();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 41:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_4;
        }
        jj_consume_token(41);
        paramForm();
      }
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    jj_consume_token(42);
  }

  static final public void paramForm() throws ParseException {
    type();
    jj_consume_token(ident);
                        fonc.ajoutParam(YakaTokenManager.identLu);
  }

  static final public void declConst() throws ParseException {
    jj_consume_token(CONST);
    defConst();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 41:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_5;
      }
      jj_consume_token(41);
      defConst();
    }
    jj_consume_token(43);
  }

  static final public void defConst() throws ParseException {
    jj_consume_token(ident);
    jj_consume_token(44);
    valConst();
  }

  static final public void valConst() throws ParseException {
                   String id = YakaTokenManager.identLu;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case entier:
      jj_consume_token(entier);
  decl.ajoutConstEntier(id,YakaTokenManager.entierLu,token);
  yvm.iconst(YakaTokenManager.entierLu);
      break;
    case ident:
      jj_consume_token(ident);
 decl.ajoutConstIdent(id,YakaTokenManager.identLu,token);
 yvm.iconst(tabIdent.chercheIdent(id).getValeur());
      break;
    case TRUE:
      jj_consume_token(TRUE);
 decl.ajoutConstBool(id,VRAI,token);
 yvm.iconst(VRAI);
      break;
    case FALSE:
      jj_consume_token(FALSE);
 decl.ajoutConstBool(id,FAUX,token);
 yvm.iconst(FAUX);
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void declVar() throws ParseException {
    jj_consume_token(VAR);
    type();
    jj_consume_token(ident);
  decl.ajoutVariable(YakaTokenManager.identLu,token);
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 41:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_6;
      }
      jj_consume_token(41);
      jj_consume_token(ident);
      decl.ajoutVariable(YakaTokenManager.identLu,token);
    }
    jj_consume_token(43);
  }

  static final public void type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ENT:
      jj_consume_token(ENT);
                 decl.definirTypeVar(ENTIER);fonc.setTypeRetour(ENTIER);
      break;
    case BOOLEEN:
      jj_consume_token(BOOLEEN);
                 decl.definirTypeVar(BOOLEAN);fonc.setTypeRetour(BOOLEAN);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

/*
 * Syntaxe des instructions.
 */
  static final public void suiteInstr() throws ParseException {
    instruction();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 43:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_7;
      }
      jj_consume_token(43);
              expr.clear();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SI:
      case RETOURNE:
      case TANTQUE:
      case ECRIRE:
      case LIRE:
      case ALALIGNE:
      case ident:
        instruction();
        break;
      default:
        jj_la1[10] = jj_gen;
        ;
      }
    }
  }

  static final public void instruction() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ident:
      affectation();
      break;
    case LIRE:
      lecture();
      break;
    case ECRIRE:
    case ALALIGNE:
      ecriture();
      break;
    case TANTQUE:
      boucle();
      break;
    case SI:
      condition();
      break;
    case RETOURNE:
      retourne();
      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void retourne() throws ParseException {
    jj_consume_token(RETOURNE);
    expression();
                                 yvm.ireturn(fonc.getTailleParam()+4);fonc.testValeurRetour(token);
  }

/*
 *affectation
 */
  static final public void affectation() throws ParseException {
    jj_consume_token(ident);
           nomVarTemp = YakaTokenManager.identLu;
    jj_consume_token(44);
    expression();
          yvm.istore(tabIdent.valeurIdent(nomVarTemp));
         expr.controleAffectation(nomVarTemp,token);
  }

  static final public void lecture() throws ParseException {
    jj_consume_token(LIRE);
    jj_consume_token(40);
    jj_consume_token(ident);
    jj_consume_token(42);
          yvm.lireEnt(tabIdent.valeurIdent(YakaTokenManager.identLu));
  }

  static final public void ecriture() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ECRIRE:
      jj_consume_token(ECRIRE);
      jj_consume_token(40);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TRUE:
      case FALSE:
      case NON:
      case entier:
      case ident:
      case 40:
      case 51:
        expression();
                                    if (expr.getSommetTypes() == Constantes.BOOLEAN) {yvm.ecrireBool();}else{yvm.ecrireEnt();}
        break;
      case chaine:
                                                                                                                                   yvm.ecrireChaine(YakaTokenManager.chaineLue);
        jj_consume_token(chaine);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(42);
      break;
    case ALALIGNE:
      jj_consume_token(ALALIGNE);
                                                                                                                                                                                                              yvm.aLaLigne();
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

/*
 * It�ration
 */
  static final public void boucle() throws ParseException {
    jj_consume_token(TANTQUE);
                     yvm.tantque();
    expression();
                                                   expr.testExprBool(token);
    jj_consume_token(FAIRE);
                                                                                         yvm.iffaux();
    suiteBoucle();
  }

  static final public void suiteBoucle() throws ParseException {
    suiteInstr();
    jj_consume_token(FAIT);
                               yvm.fait();
  }

/*
 * conditionnelle
 */
/*\ ATTENTION: conditionnelle a redéfinir, probleme  avec les étiquettes de iffaux quand il y a un bloc sinon   */
  static final public void condition() throws ParseException {
    jj_consume_token(SI);
                yvm.si();
    expression();
                                        expr.testExprBool(token);
    jj_consume_token(ALORS);
                                                                               yvm.iffaux();
    suiteInstr();
                                                                                                            yvm.sinon();
    suiteCondi();
  }

  static final public void suiteCondi() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case FSI:
      jj_consume_token(FSI);
                  yvm.fsi();
      break;
    case SINON:
      sinonCondi();
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void sinonCondi() throws ParseException {
    jj_consume_token(SINON);
    suiteInstr();
    jj_consume_token(FSI);
                                        yvm.fsi();
  }

/*
 * Expression .
 */
  static final public void expression() throws ParseException {
    simpleExpr();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 44:
    case 45:
    case 46:
    case 47:
    case 48:
    case 49:
      opRel();
      simpleExpr();
                yvm.operation(expr.getSommetOp());
  expr.operation(token);
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
  }

  static final public void simpleExpr() throws ParseException {
    terme();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
      case 50:
      case 51:
        ;
        break;
      default:
        jj_la1[16] = jj_gen;
        break label_8;
      }
      opAdd();
      terme();
   yvm.operation(expr.getSommetOp());
   expr.operation(token);
    }
  }

  static final public void terme() throws ParseException {
    facteur();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
      case 52:
      case 53:
        ;
        break;
      default:
        jj_la1[17] = jj_gen;
        break label_9;
      }
      opMul();
      facteur();
        yvm.operation(expr.getSommetOp());
  expr.operation(token);
    }
  }

  static final public void facteur() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case entier:
    case ident:
    case 40:
      primaire();
      break;
    case NON:
    case 51:
      opNeg();
      primaire();
                         yvm.operation(expr.getSommetOp());expr.operation(token);
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void primaire() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case entier:
    case ident:
      valeur();
      break;
    case 40:
      jj_consume_token(40);
      expression();
      jj_consume_token(42);
      break;
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void valeur() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case entier:
      jj_consume_token(entier);
  expr.empilerEnt(YakaTokenManager.entierLu);
  yvm.iconst(YakaTokenManager.entierLu);
      break;
    case ident:
      jj_consume_token(ident);
                 Ident i = tabIdent.chercheIdent(YakaTokenManager.identLu);
                 if (i.getVarOrConst() == Ident.CONST) {
                        yvm.iconst(i.getValeur());
                 } else if (i.getVarOrConst() == Ident.VAR || i.getVarOrConst() == Ident.PARAM) {
                        yvm.iload(i.getValeur());
                 }else if (i.getVarOrConst() == Ident.FONC) {
                        fonc.empilerFonction(YakaTokenManager.identLu);
                        yvm.reserveRetour();
                 }

                 expr.empilerIdent(YakaTokenManager.identLu,token);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 40:
        argumentsFonction();
        break;
      default:
        jj_la1[20] = jj_gen;
        ;
      }
      break;
    case TRUE:
      jj_consume_token(TRUE);
 expr.empilerBool(VRAI);
 yvm.iconst(VRAI);
      break;
    case FALSE:
      jj_consume_token(FALSE);
 expr.empilerBool(FAUX);
 yvm.iconst(FAUX);
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void argumentsFonction() throws ParseException {
                            int cmp=0;
    jj_consume_token(40);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case NON:
    case entier:
    case ident:
    case 40:
    case 51:
      expression();
                           cmp++;
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 41:
          ;
          break;
        default:
          jj_la1[22] = jj_gen;
          break label_10;
        }
        jj_consume_token(41);
        expression();
                                                     cmp++;
      }
      break;
    default:
      jj_la1[23] = jj_gen;
      ;
    }
    jj_consume_token(42);
         fonc.testTypesArguments(cmp,token);yvm.call(fonc.getNomFoncActuel());fonc.depilerFonction();
  }

  static final public void opRel() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 44:
      jj_consume_token(44);
         expr.empilerOperation(EGAL);
      break;
    case 45:
      jj_consume_token(45);
         expr.empilerOperation(NEGAL);
      break;
    case 46:
      jj_consume_token(46);
         expr.empilerOperation(INF);
      break;
    case 47:
      jj_consume_token(47);
         expr.empilerOperation(INFEG);
      break;
    case 48:
      jj_consume_token(48);
         expr.empilerOperation(SUP);
      break;
    case 49:
      jj_consume_token(49);
         expr.empilerOperation(SUPEG);
      break;
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void opAdd() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 50:
      jj_consume_token(50);
         expr.empilerOperation(PLUS);
      break;
    case 51:
      jj_consume_token(51);
           expr.empilerOperation(MOINS);
      break;
    case OR:
      jj_consume_token(OR);
                 expr.empilerOperation(OU);
      break;
    default:
      jj_la1[25] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void opMul() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 52:
      jj_consume_token(52);
         expr.empilerOperation(MUL);
      break;
    case 53:
      jj_consume_token(53);
                 expr.empilerOperation(DIV);
      break;
    case AND:
      jj_consume_token(AND);
                 expr.empilerOperation(ET);
      break;
    default:
      jj_la1[26] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void opNeg() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 51:
      jj_consume_token(51);
       expr.empilerOperation(NEG);
      break;
    case NON:
      jj_consume_token(NON);
  expr.empilerOperation(NOT);
      break;
    default:
      jj_la1[27] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public YakaTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[28];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x8100,0x80000,0x200,0x0,0x8100,0x0,0x120000,0x0,0x8100,0x0,0x52000,0x52000,0x1120000,0x0,0x4800,0x0,0x400000,0x800000,0x1120000,0x120000,0x0,0x120000,0x0,0x1120000,0x0,0x400000,0x800000,0x1000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x200,0x0,0x200,0x50,0x200,0x0,0x800,0x47,0x47,0x801d0,0x5,0x0,0x3f000,0xc0000,0x300000,0x80150,0x150,0x100,0x50,0x200,0x80150,0x3f000,0xc0000,0x300000,0x80000,};
   }

  /** Constructor with InputStream. */
  public Yaka(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Yaka(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new YakaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Yaka(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new YakaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Yaka(YakaTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(YakaTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[54];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 28; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 54; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
