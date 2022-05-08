/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author sergi
 */

import org.primefaces.model.chart.PieChartModel;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class JobMarketBean {
  private PieChartModel model;

  @PostConstruct
  public void init() {
      model = new PieChartModel();
      model.set("En Casa", 62);//jobs in thousands
      model.set("Gastronómico", 46);
      model.set("Fiesta", 38);
      model.set("Lectura", 31);
      model.set("Cine", 27);
      model.set("Playa", 14);
      model.set("Otro", 14);

      //followings are some optional customizations:
      //set title

      //set legend position to 'e' (east), other values are 'w', 's' and 'n'
      model.setLegendPosition("e");
      //enable tooltips
      model.setShowDatatip(true);
      //show labels inside pie chart
      model.setShowDataLabels(true);
      //show label text  as 'value' (numeric) , others are 'label', 'percent' (default). Only one can be used.
      model.setDataFormat("value");
      //format: %d for 'value', %s for 'label', %d%% for 'percent'
      model.setDataLabelFormatString("%dK");
      //pie sector colors
      model.setSeriesColors("aaf,afa,faa,ffa,aff,faf,ddd");
  }

  public PieChartModel getModel() {
      return model;
  }
}