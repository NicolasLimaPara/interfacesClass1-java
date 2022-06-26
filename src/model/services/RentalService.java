package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

		private Double pricePerDay;
		private Double pricePerHour;
		
		private BrazilTaxService taxService;

		public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
			super();
			this.pricePerDay = pricePerDay;
			this.pricePerHour = pricePerHour;
			this.taxService = taxService;
		}
		
		public void processInvoice (CarRental carRental) {
			
			// no java a data é armazenada em milisegundos
			
			long t1 = carRental.getStart().getTime(); //getTime pega o valor em milisegundos da data
			long t2 = carRental.getFinish().getTime();
			double hours = (double)(t2 - t1) /1000 / 60 / 60;   //convertendo para horas depois para minutos
		
			double 	basicPayment;
			if (hours <= 12.0) {
				basicPayment = Math.ceil(hours) * pricePerHour;
				
			}
			else {
				basicPayment = Math.ceil(hours/24) * pricePerDay;
			}
			
			double tax = taxService.tax(basicPayment);
			
			carRental.setInvoice(new Invoice(basicPayment, tax));
		}
		
	}	