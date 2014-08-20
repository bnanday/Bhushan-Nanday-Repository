function [ QPSK_Symbols ] = QPSK_Modulator( random_bits )
 
% This function takes the randomly generated data bits and binds 2 succesive
% bits to form a QPKS symbol. We have hard coded the values for each
% combination of 2 bits according to the QPSK constellation.

QPSK_Symbols = zeros(1,length(random_bits)/2);   % This is vector to store the generated QPSK symbols
counter=1;
for i=1:2:length(random_bits)
     if (data(i)==0 && data(i+1)==0)
        QPSK_Symbols(counter)=(1/sqrt(2))*(1+1j);
     else if (random_bits(i)==0 && random_bits(i+1)==1)
        QPSK_Symbols(counter)=(1/sqrt(2))*(1-1j);
     else if (random_bits(i)==1 && random_bits(i+1)==0)
        QPSK_Symbols(counter)=(1/sqrt(2))*(-1+1j);
     else if (random_bits(i)==1 && random_bits(i+1)==1)
        QPSK_Symbols(counter)=(1/sqrt(2))*(-1-1j);
    end        
    counter=counter+1;
end 


end

