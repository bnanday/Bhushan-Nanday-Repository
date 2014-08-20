function [ CP_Symbol ] = Add_CP( OFDM_Symbol )

CP_Symbol = zeros(548,140);
k=477;
for i = 1:1:36
    for j = 1:1:140
        
     CP_Symbol(i,j) = OFDM_Symbol(k,j);
     
    end
    k=k+1;
end

l=1;
for i = 37:1:548
    for j = 1:1:140
        
        CP_Symbol(i,j) = OFDM_Symbol(l,j);
    end
    l=l+1;
end


end
