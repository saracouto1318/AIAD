package stats;

public class StatisticsElevator extends StatisticsInfo {
    private String name;
    private int taxaOcupacao;
    private int taxaUso;

    public StatisticsElevator(String name, int taxaOcupacao, int taxaUso) {
        super(StatisticsType.ELEVATOR);
        this.name = name;
        this.taxaOcupacao = taxaOcupacao;
        this.taxaUso = taxaUso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaxaOcupacao() {
        return taxaOcupacao;
    }

    public void setTaxaOcupacao(int taxaOcupacao) {
        this.taxaOcupacao = taxaOcupacao;
    }

    public int getTaxaUso() {
        return taxaUso;
    }

    public void setTaxaUso(int taxaUso) {
        this.taxaUso = taxaUso;
    }
}
