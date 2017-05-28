/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

/**
 *
 * @author KeithW
 */
public class BGCounter {

    private String sType, sName, sImageFileName;
    private int iScore, iScoreMuliplier = 1;
    private boolean bSwappable, bExplodable, bWildCard;
    private boolean bMatched, bDelete, bExplode;

    public BGCounter() {
        this.sType = "DEFAULT";
        this.sName = "DEFAULT";
        this.sImageFileName = "DEFAULT";
        this.iScore = 0;
        iScoreMuliplier = 1;
        this.bSwappable = true;
        this.bExplodable = false;
        this.bWildCard = false;

        this.bDelete = false;
        this.bExplode = false;
        this.bMatched = false;

    }

    // Construct a basic counter
    public BGCounter(String sType, String sName, String sImageFileName, int iScore) {
        this.sType = sType;
        this.sName = sName;
        this.sImageFileName = sImageFileName;
        this.iScore = iScore;
        iScoreMuliplier = 1;
        this.bSwappable = true;
        this.bExplodable = false;
        this.bWildCard = false;

        this.bDelete = false;
        this.bExplode = false;
        this.bMatched = false;
    }

    // Construct an advanced counter
    public BGCounter(String sType, String sName, String sImageFileName, int iScore, boolean bSwappable, boolean bExplodable, boolean bWildCard) {
        this.sType = sType;
        this.sName = sName;
        this.sImageFileName = sImageFileName;
        this.iScore = iScore;
        iScoreMuliplier = 1;
        this.bSwappable = bSwappable;
        this.bExplodable = bExplodable;
        this.bWildCard = bWildCard;

        this.bDelete = false;
        this.bExplode = false;
        this.bMatched = false;

    }

    public BGCounter(BGCounter copy) {
        this.sType = copy.getType();
        this.sName = copy.getName();
        this.sImageFileName = copy.getImageFileName();
        this.iScore = copy.getScore();
        this.iScoreMuliplier = copy.getScoreMuliplier();
        this.bSwappable = copy.isSwappable();
        this.bExplodable = copy.isExplodable();
        this.bWildCard = copy.isWildCard();
        
        this.bDelete = copy.isDeleted();
        this.bExplode = copy.isExplode();
        this.bMatched = copy.isMatched();
    }

    @Override
    public String toString() {
        return "BGCounter{" + "sType=" + sType + ", sName=" + sName + ", sImageFileName=" + sImageFileName + '}';
    }

    public String getImageFileName() {

        if (isExplode() == true) {
            return "explode.png";
        } else if (isDeleted() == true) {
            return "x" + this.getScoreMuliplier() + ".png";
        } else {
            return sImageFileName;
        }

    }

    public void setImageFileName(String sImageFileName) {
        this.sImageFileName = sImageFileName;
    }

    public String getName() {

        return sName;

    }

    public void setName(String sName) {
        this.sName = sName;
    }

    public String getType() {
        return sType;
    }

    public void setType(String sType) {
        this.sType = sType;
    }

    public int getScore() {
        return iScore * iScoreMuliplier;
    }

    public void setScoreMuliplier(int iScoreMuliplier) {
        this.iScoreMuliplier = iScoreMuliplier;
    }

    public int getScoreMuliplier() {
        return iScoreMuliplier;
    }

    public boolean isSwappable() {
        return bSwappable;
    }

    public boolean isExplodable() {
        return bExplodable;
    }

    public boolean isWildCard() {
        return bWildCard;
    }

    public void setDelete(boolean bDelete) {
        this.bDelete = bDelete;
    }

    public boolean isDeleted() {
        return bDelete;
    }

    public boolean isExplode() {
        return bExplode;
    }

    public void setExplode(boolean bExplode) {
        this.bExplode = bExplode;
    }

    public boolean isMatched() {
        return bMatched;
    }

    public void setMatched(boolean bMatched) {
        this.bMatched = bMatched;
    }
}
