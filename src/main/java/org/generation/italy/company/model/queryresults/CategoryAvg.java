package org.generation.italy.company.model.queryresults;

public class CategoryAvg {
    private Integer id;
    private Double avg;

    public CategoryAvg(Integer id, Double avg) {
        this.id = id;
        this.avg = avg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
}
