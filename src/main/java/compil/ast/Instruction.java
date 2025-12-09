package compil.ast;

/**
 * Instruction, classe abstraite pour Instruction*.
 */
public abstract class Instruction extends AstNode {
    Instruction(AstNode... enfants) {
        super(enfants);
    }
}
