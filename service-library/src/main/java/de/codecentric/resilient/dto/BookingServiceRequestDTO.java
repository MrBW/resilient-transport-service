package de.codecentric.resilient.dto;

/**
 * Booking Request DTO
 * @author Benjamin Wilms
 */
public class BookingServiceRequestDTO extends AbstractDTO {

    private CustomerDTO customerDTO;

    private AddressDTO senderAddress;

    private AddressDTO receiverAddress;

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public AddressDTO getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(AddressDTO senderAddress) {
        this.senderAddress = senderAddress;
    }

    public AddressDTO getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(AddressDTO receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookingServiceRequestDTO{");
        sb.append("customerDTO=").append(customerDTO);
        sb.append(", senderAddress=").append(senderAddress.toString());
        sb.append(", receiverAddress=").append(receiverAddress.toString());
        sb.append('}');
        return sb.toString();
    }
}
