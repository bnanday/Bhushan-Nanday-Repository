function [ Final_Symbol ] = Upconverter( CP_Symbol )

Final_Symbol = zeros(2192,140);


counter=1;

for m = 1:4:2192
    Final_Symbol(m,:) = CP_Symbol(counter,:);
    counter=counter+1;
end
end
