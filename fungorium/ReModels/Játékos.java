package fungorium.ReModels;

public abstract class Játékos {
    protected String name;
    private int winCondition = 0; // az a mutato, ami a nyeresnel lesz osszehasonlitva a tobbivel

    public void setName(String n)
    {
        name = n;
    }

    public void addWinCondition()
    {
        winCondition += 1;
    }

    public int getWinCondition()
    {
        return winCondition;
    }

    public String getName()
    {
        return name;
    }
}
