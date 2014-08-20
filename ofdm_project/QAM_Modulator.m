function [ QAM_Symbols ] = QAM( random_bits )

QAM_Symbols=zeros(1,length(random_bits)/4);

counter=1;

for i=1:4:length(random_bits)
    
    if (random_bits(i)==0 && random_bits(i+1)==0 && random_bits(i+2)==0 && random_bits(i+3)==0) 
       QAM_Symbols(counter)=(1/sqrt(10))*(1+1j);
    elseif (random_bits(i)==0 && random_bits(i+1)==0 && random_bits(i+2)==0 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(1+3j);
    elseif (random_bits(i)==0 && random_bits(i+1)==0&& random_bits(i+2)==1 && random_bits(i+3)==0)
        QAM_Symbols(counter)=(1/sqrt(10))*(3+1j);
    elseif (random_bits(i)==0 && random_bits(i+1)==0&& random_bits(i+2)==1 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(3+3j);
    elseif (random_bits(i)==0 && random_bits(i+1)==1&& random_bits(i+2)==0 && random_bits(i+3)==0)
        QAM_Symbols(counter)=(1/sqrt(10))*(1-1j);
    elseif (random_bits(i)==0 && random_bits(i+1)==1&& random_bits(i+2)==0 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(1-3j);
    elseif (random_bits(i)==0 && random_bits(i+1)==1&& random_bits(i+2)==1 && random_bits(i+3)==0)
        QAM_Symbols(counter)=(1/sqrt(10))*(3-1j);
    elseif (random_bits(i)==0 && random_bits(i+1)==1&& random_bits(i+2)==1 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(3-3j);
    elseif (random_bits(i)==1 && random_bits(i+1)==0&& random_bits(i+2)==0 && random_bits(i+3)==0)
        QAM_Symbols(counter)=(1/sqrt(10))*(-1+1j);
    elseif (random_bits(i)==1 && random_bits(i+1)==0&& random_bits(i+2)==0 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(-1+3j);
    elseif (random_bits(i)==1 && random_bits(i+1)==0&& random_bits(i+2)==1 && random_bits(i+3)==0)
        QAM_Symbols(counter)=(1/sqrt(10))*(-3+1j);
    elseif (random_bits(i)==1 && random_bits(i+1)==0&& random_bits(i+2)==1 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(-3+3j);
    elseif (random_bits(i)==1 && random_bits(i+1)==1&& random_bits(i+2)==0 && random_bits(i+3)==0)
        QAM_Symbols(counter)=(1/sqrt(10))*(-1-1j);
    elseif (random_bits(i)==1 && random_bits(i+1)==1&& random_bits(i+2)==0 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(-1-3j);
    elseif (random_bits(i)==1 && random_bits(i+1)==1&& random_bits(i+2)==1 && random_bits(i+3)==0)
        QAM_Symbols(counter)=(1/sqrt(10))*(-3-1j);
    elseif (random_bits(i)==1 && random_bits(i+1)==1&& random_bits(i+2)==1 && random_bits(i+3)==1)
        QAM_Symbols(counter)=(1/sqrt(10))*(-3-3j);  
    end 
    
    counter=counter+1;
    
end 


end

